package com.br.security_system_backend.repository;

import com.br.security_system_backend.model.SensorEvent;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface SensorEventRepository extends CassandraRepository<SensorEvent, UUID> {
}
