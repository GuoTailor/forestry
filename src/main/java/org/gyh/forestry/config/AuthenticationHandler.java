package org.gyh.forestry.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.ResponseInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

/**
 * create by GYH on 2023/5/12
 */
@Slf4j
public class AuthenticationHandler implements AuthenticationSuccessHandler,
        AuthenticationFailureHandler,
        AccessDeniedHandler,
        SecurityContextRepository,
        AuthenticationEntryPoint {
    private final ObjectMapper json = new ObjectMapper();
    private final RedisTemplate<String, Object> redisTemplate;

    public AuthenticationHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 未登录时做的操作，重写不跳转登录页
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        log.info("未登录 {} {}", httpServletRequest.getRequestURI(), e.getLocalizedMessage());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(403);
        try {
            httpServletResponse.getWriter().write(json.writeValueAsString(ResponseInfo.failed(e.getLocalizedMessage())));
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        log.info("授权失败 {} {}", httpServletRequest.getRequestURI(), e.getLocalizedMessage());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(403);
        try {
            httpServletResponse.getWriter().write(json.writeValueAsString(ResponseInfo.failed(e.getLocalizedMessage())));
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        log.info("登录失败", e);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        String msg = e.getLocalizedMessage();
        try {
            httpServletResponse.getWriter().write(json.writeValueAsString(ResponseInfo.failed(msg)));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        log.info("登录成功 {} {}", authentication, authentication.getCredentials());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) authentication;
        String token = UUID.randomUUID().toString();
        User user = (User) userToken.getPrincipal();
        String redisKey = Constant.tokenKey + user.getUsername() + Constant.tokenInfix + token;
        try {
            String value = json.writeValueAsString(new ResponseInfo<>(ResponseInfo.OK_CODE, "登录成功", token));
            Duration expired = Duration.ofMillis(Constant.tokenTtlMillis);
            Set<String> keys = redisTemplate.keys(Constant.tokenKey + user.getUsername() + Constant.tokenInfix + "*");
            if (keys != null) {
                redisTemplate.delete(keys);
            }
            redisTemplate.opsForValue().set(redisKey, user, expired);
            httpServletResponse.getWriter().write(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder httpRequestResponseHolder) {
        String authHeader = httpRequestResponseHolder.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        String authToken = null;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            authToken = authHeader.replaceFirst("Bearer ", "");
        } else if (Objects.equals("/room", httpRequestResponseHolder.getRequest().getRequestURI())) {
            authToken = getQueryMap(httpRequestResponseHolder.getRequest().getQueryString()).get("bearer");
        }
        if (authToken != null) {
            try {
                Set<String> keys = redisTemplate.keys(Constant.tokenKey + "*" + Constant.tokenInfix + authToken);
                if (!CollectionUtils.isEmpty(keys)) {
                    for (String key : keys) {
                        User user = (User) redisTemplate.opsForValue().get(key);
                        log.info("授权取用户信息 {}", user);
                        if (user != null) {
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
                            return new SecurityContextImpl(authentication);
                        }
                    }
                }
            } catch (BadCredentialsException e) {
                log.error(e.getLocalizedMessage());
            }
        }
        return new SecurityContextImpl();
    }

    private Map<String, String> getQueryMap(String queryStr) {
        HashMap<String, String> queryMap = new HashMap<>();
        if (StringUtils.hasLength(queryStr)) {
            String[] queryParam = queryStr.split("&");
            for (String s : queryParam) {
                String[] kv = s.split("=", 2);
                String value = kv.length == 2 ? kv[1] : "";
                queryMap.put(kv[0], value);
            }
        }
        return queryMap;
    }


    @Override
    public void saveContext(SecurityContext securityContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("保存上下文{}", securityContext.getAuthentication());
    }

    @Override
    public boolean containsContext(HttpServletRequest httpServletRequest) {
        log.info("包含nm");
        return false;
    }

}
