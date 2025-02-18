package com.ljw.logalarm.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
@Slf4j
public class TraceIdFilter extends OncePerRequestFilter {

    public static final String TRACE_ID = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.debug("[TraceIdFilter] begin");
            String traceId = request.getHeader(TRACE_ID);
            if (StringUtils.isEmpty(traceId)) {
                traceId = genTraceId();
            }
            MDC.put(TRACE_ID, traceId);
            filterChain.doFilter(request, response);
        }finally {
            log.debug("[TraceIdFilter] end");
            MDC.clear();
        }
    }
    public static String genTraceId(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}