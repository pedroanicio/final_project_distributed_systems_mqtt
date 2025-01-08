package com.br.security_system_backend;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.InetSocketAddress;


@SpringBootApplication
public class SecuritySystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuritySystemBackendApplication.class, args);

	}

}
