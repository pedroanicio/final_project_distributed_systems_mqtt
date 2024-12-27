package com.br.security_system_backend.service;

import com.br.security_system_backend.model.SensorEvent;
import com.br.security_system_backend.repository.SensorEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class SensorEventService {

    private final SensorEventRepository sensorEventRepository;


    public SensorEventService(SensorEventRepository sensorEventRepository) {
        this.sensorEventRepository = sensorEventRepository;
    }

    public SensorEvent saveEvent(String sensorId, String eventType, String value) {
        SensorEvent event = new SensorEvent();
        event.setId(UUID.randomUUID());
        event.setSensorId(sensorId);
        event.setTimestamp(new Date());
        event.setEventType(eventType);
        event.setValue(value);
        return sensorEventRepository.save(event);
    }
}
