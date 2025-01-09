package com.ljw.logalarm.autoconfigure;

import com.ljw.logalarm.core.dto.TimeoutSettingDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "log-alarm.timeout")
public class TimeoutProperties {

    private List<TimeoutSettingDTO> settings;

    public List<TimeoutSettingDTO> getSettings() {
        return settings;
    }

    public void setSettings(List<TimeoutSettingDTO> settings) {
        this.settings = settings;
    }

}
