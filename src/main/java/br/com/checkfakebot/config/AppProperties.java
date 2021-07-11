package br.com.checkfakebot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppProperties {

	private String urlservice;
	
}
