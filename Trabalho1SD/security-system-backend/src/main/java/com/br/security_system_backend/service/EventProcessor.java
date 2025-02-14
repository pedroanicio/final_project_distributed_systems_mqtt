package com.br.security_system_backend.service;


import org.springframework.stereotype.Service;


@Service
public class EventProcessor {

    private final ActuatorCommandService actuatorCommandService;

    public EventProcessor(ActuatorCommandService actuatorCommandService) {
        this.actuatorCommandService = actuatorCommandService;
    }

    public void processSensorEvent(String sensorId, String eventType, String value) {
        switch (eventType.toLowerCase()) {
            case "motion":
                if (value.equalsIgnoreCase("detected")) {
                    actuatorCommandService.sendCommand("door_actuator", "lock", "");
                    actuatorCommandService.sendCommand("alarm_actuator", "ON", "");
                }
                break;

            case "smoke":
                if (value.equalsIgnoreCase("detected")) {
                    actuatorCommandService.sendCommand("water_actuator", "ON", "");
                }
                break;

            case "high_temperature":
                if (value.equalsIgnoreCase("detected")) {
                    actuatorCommandService.sendCommand("air_conditioner_actuator", "ON", "16");
                }
                break;

            case "gas_leak":
                if (value.equalsIgnoreCase("detected")) {
                    actuatorCommandService.sendCommand("ventilation_actuator", "ON", "100");
                    actuatorCommandService.sendCommand("alarm_actuator", "ON", "");
                }
                break;

            case "water_leak":
                if (value.equalsIgnoreCase("detected")) {
                    actuatorCommandService.sendCommand("main_valve_actuator", "CLOSE", "");
                }
                break;

            case "earthquake":
                if (value.equalsIgnoreCase("detected")) {
                    actuatorCommandService.sendCommand("elevator_actuator", "STOP", "");
                    actuatorCommandService.sendCommand("door_actuator", "UNLOCK", "");
                }
                break;
            default:
                System.out.println("Evento não reconhecido: " + eventType);
        }
    }
}
