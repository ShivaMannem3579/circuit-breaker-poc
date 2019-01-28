package com.example.circuitbreakerpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCircuitBreaker
public class CircuitBreakerPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(CircuitBreakerPocApplication.class, args);
	}


	@Bean(name="hystrixRestTemplate")
	public RestTemplate hystrixRestTemplate() {
		return new RestTemplate();
	}

}

