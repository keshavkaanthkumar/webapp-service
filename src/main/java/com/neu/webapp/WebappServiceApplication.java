package com.neu.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class WebappServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappServiceApplication.class, args);
	}

}
