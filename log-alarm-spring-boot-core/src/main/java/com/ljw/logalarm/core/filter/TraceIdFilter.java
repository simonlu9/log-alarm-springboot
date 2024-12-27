package com.ljw.logalarm.core.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.UUID;
@Slf4j
public class TraceIdFilter extends OncePerRequestFilter {

    public static final String TRACE_ID = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String traceId = request.getHeader(TRACE_ID);
            if (StringUtils.isEmpty(traceId)) {
                traceId = genTraceId();
            }
            MDC.put(TRACE_ID, traceId);
            filterChain.doFilter(request, response);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            MDC.clear();
        }
    }
    public static String genTraceId(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}