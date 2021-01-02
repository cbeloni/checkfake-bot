package br.com.checkfakebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CheckfakeBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckfakeBotApplication.class, args);
	}

}
