package com.br.security_system_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;


@Service
public class EventProcessor {

    private final ActuatorCommandService actuatorCommandService;

    public EventProcessor(ActuatorCommandService actuatorCommandService) {
        this.actuatorCommandService = actuatorCommandService;
    }

    public void processSensorEvent(String sensorId, String eventType, String value) {
        if (eventType.equals("motion") && value.equalsIgnoreCase("detected")) {
            actuatorCommandService.sendCommand("door_actuator", "lock", "");
            actuatorCommandService.sendCommand("alarm_actuator", "ON", "");
        }

        if(eventType.equals("noisy") && value.equalsIgnoreCase("tooLoud")){
            actuatorCommandService.sendCommand("test", "", "");
        }

        if (eventType.equals("smoke") && value.equalsIgnoreCase("detected")) {
            actuatorCommandService.sendCommand("water_actuator", "ON", "");
        }

        if (eventType.equals("high_temperature") && value.equalsIgnoreCase("detected")) {
            actuatorCommandService.sendCommand("ligar ar condicionado", "ON","");
        }

    }
}
