package com.ljw.logalarm.core.filter;

import com.ljw.logalarm.core.dto.TimeoutSettingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class ExecutionTimeFilter extends OncePerRequestFilter {
    private List<TimeoutSettingDTO> timeoutSettings;

    public ExecutionTimeFilter(List<TimeoutSettingDTO> timeoutSettings) {
        this.timeoutSettings = timeoutSettings;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 记录请求开始时间
        long startTime = System.currentTimeMillis();
        String requestURI = request.getRequestURI();
        try {
            // 执行过滤器链
            filterChain.doFilter(request, response);
        } finally {
            // 记录请求结束时间
            if(timeoutSettings!=null){
                long endTime = System.currentTimeMillis();

                // 计算并输出方法执行时长
                long duration = endTime - startTime;
                for (TimeoutSettingDTO setting : timeoutSettings) {
                    // 使用正则匹配 URL
                    if (Pattern.matches(setting.getUrlPattern(), requestURI)) {
                        if (duration > setting.getThreshold()) {
                            log.error("Request to [{}] exceeded timeout. Duration: {} ms, Threshold: {} ms",
                                    requestURI, duration, setting.getThreshold());
                        }
                        break; // 找到匹配的 URL 后退出循环
                    }
                }
            }
        }
    }
}
