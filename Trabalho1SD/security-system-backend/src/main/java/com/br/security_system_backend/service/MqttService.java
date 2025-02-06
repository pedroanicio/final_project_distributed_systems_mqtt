package com.br.security_system_backend.service;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    private final MessageChannel mqttOutboundChannel;

    public MqttService(MessageChannel mqttOutboundChannel) {
        this.mqttOutboundChannel = mqttOutboundChannel;
    }

    public void sendCommand(String topic, String payload) {
        try {
            // Log para debug
            System.out.println("Sending MQTT message: " + topic + " -> " + payload);
            mqttOutboundChannel.send(MessageBuilder.withPayload(payload)
                    .setHeader("mqtt_topic", topic)
                    .build());
        } catch (Exception e) {
            System.err.println("Failed to send MQTT command: " + e.getMessage());
        }
    }

}

