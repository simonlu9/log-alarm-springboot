package com.ljw.logalarm.core.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
public class ExecutionTimeFilter extends OncePerRequestFilter {
    private long timeout;

    public ExecutionTimeFilter(long timeout) {
        this.timeout = timeout;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 记录请求开始时间
        long startTime = System.currentTimeMillis();

        try {
            // 执行过滤器链
            filterChain.doFilter(request, response);
        } finally {
            // 记录请求结束时间
            long endTime = System.currentTimeMillis();

            // 计算并输出方法执行时长
            long executionTime = endTime - startTime;

            // 通过请求获取更多的信息（可选）
            if (executionTime>timeout) {
//                HttpServletRequest httpRequest = (HttpServletRequest) request;
//                String requestUri = httpRequest.getRequestURI();
//                String method = httpRequest.getMethod();
                // 输出请求方法及其执行时长
                log.error("Execution Time: " + executionTime/1000f + " s");
            }
        }
    }
}
