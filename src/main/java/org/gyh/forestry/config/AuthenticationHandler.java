package org.gyh.forestry.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.OperationRecord;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.service.OperationRecordService;
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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    private final OperationRecordService operationRecordService;

    public AuthenticationHandler(RedisTemplate<String, Object> redisTemplate, OperationRecordService operationRecordService) {
        this.redisTemplate = redisTemplate;
        this.operationRecordService = operationRecordService;
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
            String requestJson = json.writeValueAsString(ResponseInfo.failed(msg));
            httpServletResponse.getWriter().write(requestJson);
            record(httpServletRequest, "登录失败", false, requestJson);
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
        User user = (User) userToken.getPrincipal();
        String accountRedisKey = Constant.accountPrefix + user.getUsername();
        String token = (String) redisTemplate.opsForValue().get(accountRedisKey);
        Duration expired = Duration.ofMillis(Constant.tokenTtlMillis);
        try {
            if (token != null) {
                String tokenRedisKey = Constant.tokenKey + token;
                redisTemplate.delete(tokenRedisKey);
            }
            token = UUID.randomUUID().toString();
            String value = json.writeValueAsString(new ResponseInfo<>(ResponseInfo.OK_CODE, "登录成功", token));
            redisTemplate.opsForValue().set(Constant.tokenKey + token, user, expired);
            redisTemplate.opsForValue().set(accountRedisKey, token, expired);
            httpServletResponse.getWriter().write(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void record(HttpServletRequest httpServletRequest, String operation, boolean success, String requestJson) {
        OperationRecord record = new OperationRecord();
        record.setModelName("登录");
        record.setOperation(operation);
        record.setMethod("POST");
        record.setUrl("/login");
        record.setIp(httpServletRequest.getRemoteAddr());
        record.setState(success);
        record.setFunction("login");
        record.setRequest("\"username\":" + httpServletRequest.getParameter("username") + "," + "\"password\":" + httpServletRequest.getParameter("password"));
        record.setResponse(requestJson);
        record.setCreateTime(LocalDateTime.now());
        record.setOperationUser(httpServletRequest.getParameter("username"));
        operationRecordService.insertSelective(record);
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder httpRequestResponseHolder) {
        String authHeader = httpRequestResponseHolder.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        String authToken;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            authToken = authHeader.replaceFirst("Bearer ", "");
        } else {
            authToken = getQueryMap(httpRequestResponseHolder.getRequest().getQueryString()).get("access_token");
        }
        if (authToken != null) {
            try {
                User user = (User) redisTemplate.opsForValue().get(Constant.tokenKey + authToken);
                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, "", List.of());
                    return new SecurityContextImpl(authentication);
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
