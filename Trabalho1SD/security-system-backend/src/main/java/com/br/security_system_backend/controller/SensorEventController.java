package com.br.security_system_backend.controller;

import com.br.security_system_backend.model.SensorEvent;
import com.br.security_system_backend.service.SensorEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/sensors")
public class SensorEventController {


    private final SensorEventService sensorEventService;

    public SensorEventController(SensorEventService sensorEventService) {
        this.sensorEventService = sensorEventService;
    }

    @PostMapping("/event")
    public ResponseEntity<SensorEvent> createEvent(@RequestBody Map<String, String> request) {
        String sensorId = request.get("sensorId");
        String eventType = request.get("eventType");
        String value = request.get("value");
        SensorEvent event = sensorEventService.saveEvent(sensorId, eventType, value);
        return ResponseEntity.ok(event);
    }
}
