package org.gyh.forestry.config;

import org.gyh.forestry.domain.Role;
import org.gyh.forestry.domain.vo.MenuWithRole;
import org.gyh.forestry.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.List;

/**
 * create by GYH on 2023/7/10
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private MenuService menuService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationHandler authenticationHandler = new AuthenticationHandler(redisTemplate);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/error",
                                "/swagger-ui/*", "/swagger-resources/*", "/v3/api-docs/*", "/webjars/*",
                                "/*.html", "/*.js", "/*.css", "/*.png", "/*.ico", "/lnfraredCamera/*"
                        ).permitAll()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .anyRequest().access((authentication, object) -> {
                            boolean granted = false;
                            boolean isMatch = false;
                            //1. 根据当前请求分析出来当前请求属于 menu 中的哪一种 http://localhost:8080/personnel/ec/hello（menu）
                            //1.1 获取当前请求 url 地址
                            String requestURI = object.getRequest().getRequestURI();
                            //1.2 和 menu 表中的记录进行比较
                            List<MenuWithRole> menuWithRoles = menuService.getAllMenusWithRole();
                            Authentication authe = authentication.get();
                            for (MenuWithRole menuWithRole : menuWithRoles) {
                                log.info(menuWithRole.getUrl());
                                if (antPathMatcher.match(menuWithRole.getUrl(), requestURI)) {
                                    isMatch = true;
                                    //如果匹配上了，说明当前请求的菜单就找到了
                                    //2. 根据第一步分析的结果，进而分析出来当前 menu 需要哪些角色才能访问（menu_role）
                                    List<Role> roles = menuWithRole.getRoles();
                                    //3. 判断当前用户是否具备本次请求所需要的角色
                                    //获取当前登录用户所具备的角色
                                    Collection<? extends GrantedAuthority> authorities = authe.getAuthorities();
                                    for (GrantedAuthority authority : authorities) {
                                        for (Role role : roles) {
                                            if (authority.getAuthority().equals(role.getAuthority())) {
                                                //说明当前用户具备所需要的角色
                                                granted = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!isMatch) {
                                //也有可能 for 循环结束了，但是和数据库中的任何一项都没有匹配上
                                //例如 http://localhost:8080/menus
                                //此时，判断用户是否已经登录，如果登录，则允许访问，否则不允许
                                if (authe instanceof UsernamePasswordAuthenticationToken) {
                                    //说明用户登录了
                                    granted = true;
                                }
                            }
                            //如果 granted 为 true，表示请求通过；granted 为 false 表示请求不通过（即用户权限不足）
                            return new AuthorizationDecision(granted);
                        })
                )
                .formLogin(form -> form
                        .successHandler(authenticationHandler)
                        .failureHandler(authenticationHandler)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(authenticationHandler)
                        .authenticationEntryPoint(authenticationHandler)
                )
                .securityContext(context -> context.securityContextRepository(authenticationHandler))
        ;
        return http.build();
    }

    /**
     * 添加角色继承关系
     *
     * @return RoleHierarchy
     */
    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        var hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy(Role.SUPER_ADMIN + " > " + Role.ADMIN + "\n" +
                Role.ADMIN + " > " + Role.USER);
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(hierarchy);
        return expressionHandler;
    }

    /**
     * 解决跨域
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
