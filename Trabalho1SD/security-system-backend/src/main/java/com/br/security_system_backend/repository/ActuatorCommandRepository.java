package com.br.security_system_backend.repository;

import com.br.security_system_backend.model.ActuatorCommand;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface ActuatorCommandRepository extends CassandraRepository<ActuatorCommand, UUID> {
}

