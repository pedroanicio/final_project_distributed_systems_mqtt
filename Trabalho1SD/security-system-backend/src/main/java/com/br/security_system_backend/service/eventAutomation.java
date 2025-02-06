package com.br.security_system_backend.service;

import org.springframework.scheduling.annotation.Scheduled;

public class eventAutomation {

    private final SensorEventService sensorEventService;

    public eventAutomation(SensorEventService sensorEventService) {
        this.sensorEventService = sensorEventService;
    }

}
