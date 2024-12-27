package com.br.security_system_backend.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table("sensor_events")
public class SensorEvent {

    @PrimaryKey
    private UUID id;

    @Column("sensor_id")
    private String sensorId;

    @Column("timestamp")
    private Date timestamp;

    @Column("event_type")
    private String eventType;

    @Column("value")
    private String value;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SensorEvent() {

    }

    public SensorEvent(UUID id, String sensorId, Date timestamp, String eventType, String value) {
        this.id = id;
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.eventType = eventType;
        this.value = value;
    }
}
