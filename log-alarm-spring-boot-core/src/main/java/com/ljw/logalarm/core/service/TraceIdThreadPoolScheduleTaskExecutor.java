package com.ljw.logalarm.core.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.util.ErrorHandler;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

import static com.ljw.logalarm.core.filter.TraceIdFilter.TRACE_ID;
import static com.ljw.logalarm.core.filter.TraceIdFilter.genTraceId;

/**
 * @author lujianwen9@gmail.com
 * @since 2024-08-15 17:13
 */
@Slf4j
public class TraceIdThreadPoolScheduleTaskExecutor extends ThreadPoolTaskScheduler {


    private Runnable wrapTaskWithMDC(Runnable task) {
        return TaskUtils.decorateTaskWithErrorHandler(() -> {
            try {
                 //设置 traceId 到 MDC（如果不存在）
                Map<String, String> context = MDC.getCopyOfContextMap();
                if(context==null||StringUtils.isEmpty(context.get(TRACE_ID))){
                    String traceId = genTraceId();
                    MDC.put(TRACE_ID, traceId);
                }
                // 执行原始任务
                task.run();
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }finally {
                // 清除 MDC 中的 traceId
                MDC.clear();
            }
        }, null,true); // 错误处理器（可定制）
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        return super.schedule(wrapTaskWithMDC(task), trigger);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Duration period) {
        return super.scheduleAtFixedRate(wrapTaskWithMDC(task), period);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Instant startTime) {
        return super.schedule(wrapTaskWithMDC(task), startTime);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Instant startTime, Duration period) {
        return super.scheduleAtFixedRate(wrapTaskWithMDC(task), startTime, period);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Duration delay) {
        return super.scheduleWithFixedDelay(wrapTaskWithMDC(task), delay);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Instant startTime, Duration delay) {
        return super.scheduleWithFixedDelay(wrapTaskWithMDC(task), startTime, delay);
    }

}
