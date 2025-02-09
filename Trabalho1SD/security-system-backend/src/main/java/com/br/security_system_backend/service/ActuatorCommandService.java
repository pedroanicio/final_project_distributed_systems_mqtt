package com.br.security_system_backend.service;

import com.br.security_system_backend.model.ActuatorCommand;
import com.br.security_system_backend.repository.ActuatorCommandRepository;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ActuatorCommandService {

    private final MessageChannel mqttOutboundChannel;

    private final ActuatorCommandRepository actuatorCommandRepository;
    private final MqttService mqttService;

    public ActuatorCommandService(MessageChannel mqttOutboundChannel, ActuatorCommandRepository actuatorCommandRepository, MqttService mqttService) {
        this.mqttOutboundChannel = mqttOutboundChannel;
        this.actuatorCommandRepository = actuatorCommandRepository;
        this.mqttService = mqttService;
    }

    public ActuatorCommand sendCommand(String actuatorId, String commandType, String parameters) {
        String topic = "actuator/" + actuatorId + "/command";

        Message<String> message = MessageBuilder.withPayload(actuatorId + "-" + commandType)
                .setHeader(MqttHeaders.TOPIC, topic)
                .setHeader(MqttHeaders.QOS, 1)
                .build();
        mqttOutboundChannel.send(message);
        System.out.println("Comando enviado para " + topic + ": " + commandType);

        ActuatorCommand command = new ActuatorCommand();
        command.setId(UUID.randomUUID());
        command.setActuatorId(actuatorId);
        command.setTimestamp(new Date());
        command.setCommandType(commandType);
        command.setParameters(parameters);

        actuatorCommandRepository.save(command);

        return command;
    }
}

