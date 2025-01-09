package com.ljw.logalarm.core.dto;

/**
 * @author lujianwen@wsyxmall.com
 * @since 2025-01-09 14:08
 */
public class TimeoutSettingDTO {
    private String urlPattern; // 正则表达式
    private long threshold; // 超时时间

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }
}
