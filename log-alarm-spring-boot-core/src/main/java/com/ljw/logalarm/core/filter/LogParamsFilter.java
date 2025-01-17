package com.ljw.logalarm.core.filter;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lujianwen9@gmail.com
 * @since 2024-09-05 11:50
 */
@Slf4j
//@Deprecated
public class LogParamsFilter extends OncePerRequestFilter {
    public static final String APP_NAME = "appName";
    public static final String REQUEST_METHOD = "requestMethod";
    public static final String REQUEST_URL = "requestUrl";
    public static final String REQUEST_PARAMS = "requestParams";
    public static final String REQUEST_BODY = "requestBody";
    public static final String USER_ID = "userId";
    private final String applicationName;

    public LogParamsFilter(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 提前获得参数，避免 XssFilter 过滤处理
        Map<String, String> queryString = buildParametersMap(request);
        String requestBody = CacheRequestBodyFilter.isJsonRequest(request) ? getBody(request) : null;
        MDC.put(APP_NAME, applicationName);
        MDC.put(REQUEST_METHOD, request.getMethod());
        MDC.put(REQUEST_URL, request.getRequestURI());
        MDC.put(REQUEST_PARAMS, JSONObject.from(queryString).toString());
        MDC.put(REQUEST_BODY,requestBody);
        chain.doFilter(request, response);

    }
    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }
    private String getBody(HttpServletRequest request){
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
