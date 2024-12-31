package com.br.security_system_backend.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table("actuator_commands")
public class ActuatorCommand {

    @PrimaryKey
    private UUID id;

    @Column("actuator_id")
    private String actuatorId;

    @Column("timestamp")
    private Date timestamp;

    @Column("command_type")
    private String commandType;

    @Column("parameters")
    private String parameters;

    public ActuatorCommand() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getActuatorId() {
        return actuatorId;
    }

    public void setActuatorId(String actuatorId) {
        this.actuatorId = actuatorId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public ActuatorCommand(UUID id, String actuatorId, Date timestamp, String commandType, String parameters) {
        this.id = id;
        this.actuatorId = actuatorId;
        this.timestamp = timestamp;
        this.commandType = commandType;
        this.parameters = parameters;
    }
}
