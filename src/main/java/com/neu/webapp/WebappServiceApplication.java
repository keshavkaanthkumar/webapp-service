package com.neu.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;


@SpringBootApplication
@EnableJpaAuditing
public class WebappServiceApplication {
	private static final Logger LOGGER=LoggerFactory.getLogger(WebappServiceApplication.class);
	public static void main(String[] args) {
		LOGGER.info("Application started");
		SpringApplication.run(WebappServiceApplication.class, args);
		
	}

}
