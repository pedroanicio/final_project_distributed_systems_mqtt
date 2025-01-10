package com.br.security_system_backend.config;

import com.br.security_system_backend.service.SensorEventService;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{"tcp://mqtt:1883"});
        //tcp://127.0.0.1:1883
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter inboundAdapter(MqttPahoClientFactory mqttClientFactory) {
        String uniqueClientId = "middleware-subscriber-" + System.nanoTime();
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                uniqueClientId,
                mqttClientFactory,
                "sensor/#"
        );
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler messageHandler(SensorEventService sensorEventService) {
        return message -> {
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            String payload = message.getPayload().toString();

            // Ignorar mensagens originadas pelo próprio serviço
            if (payload.contains("\"origin\":\"middleware\"")) {
                //System.out.println("Ignoring self-published message.");
                return;
            }

            String[] topicParts = topic.split("/");
            if (topicParts.length > 1 && "sensor".equals(topicParts[0])) {
                sensorEventService.saveEvent(topicParts[1], topicParts[2], payload);
            }

            System.out.println("Received from topic [" + topic + "]: " + payload);
        };
    }


    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutboundHandler(MqttPahoClientFactory mqttClientFactory) {
        String uniqueClientId = "middleware-publisher-" + System.nanoTime();
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(uniqueClientId, mqttClientFactory);
        handler.setAsync(true); // Envio assíncrono para melhorar desempenho
        handler.setDefaultTopic("actuator/commands");
        handler.setCompletionTimeout(5000); // Timeout para envio
        handler.setDefaultQos(1); // QOS 1 (entregar pelo menos uma vez)
        return handler;
    }


    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
}
