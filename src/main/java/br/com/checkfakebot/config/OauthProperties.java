package br.com.checkfakebot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "oauth")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OauthProperties {

	private String consumerkey;
	private String consumersecret;
	private String accesstoken;
	private String accesstokensecret;
	
}
