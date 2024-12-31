package com.br.security_system_backend.controller;

import com.br.security_system_backend.model.ActuatorCommand;
import com.br.security_system_backend.service.ActuatorCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/actuators")
public class ActuatorCommandController {

    private final ActuatorCommandService actuatorCommandService;

    public ActuatorCommandController(ActuatorCommandService actuatorCommandService) {
        this.actuatorCommandService = actuatorCommandService;
    }

    @PostMapping("/command")
    public ResponseEntity<ActuatorCommand> createCommand(@RequestBody Map<String, String> request) {
        String actuatorId = request.get("actuatorId");
        String commandType = request.get("commandType");
        String parameters = request.get("parameters");
        ActuatorCommand command = actuatorCommandService.sendCommand(actuatorId, commandType, parameters);
        return ResponseEntity.ok(command);
    }
}

