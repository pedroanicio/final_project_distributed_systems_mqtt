package com.br.security_system_backend.service;

import com.br.security_system_backend.model.ActuatorCommand;
import com.br.security_system_backend.repository.ActuatorCommandRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ActuatorCommandService {

    private final ActuatorCommandRepository actuatorCommandRepository;
    private final MqttService mqttService;

    public ActuatorCommandService(ActuatorCommandRepository actuatorCommandRepository, MqttService mqttService) {
        this.actuatorCommandRepository = actuatorCommandRepository;
        this.mqttService = mqttService;
    }

    public ActuatorCommand sendCommand(String actuatorId, String commandType, String parameters) {
        ActuatorCommand command = new ActuatorCommand();
        command.setId(UUID.randomUUID());
        command.setActuatorId(actuatorId);
        command.setTimestamp(new Date());
        command.setCommandType(commandType);
        command.setParameters(parameters);

        actuatorCommandRepository.save(command);

        // Publica no MQTT
        //String topic = "actuator/" + actuatorId;
        //mqttService.sendCommand(topic, parameters);

        return command;
    }
}

