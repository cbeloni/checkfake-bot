package br.com.checkfakebot.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.checkfakebot.config.OauthProperties;
import br.com.checkfakebot.util.HMAC;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
@Slf4j
public class WebHookService {
	
	@Autowired
	private OauthProperties oauthProperties;
	
	public String listenerWebhook(Object body) throws JsonGenerationException, JsonMappingException, IOException {
		
		String json = new ObjectMapper().writeValueAsString(body);

		log.info("Objeto recebido: " + json);
		return json;
	}
	
	public String getWebHook(String crcToken) throws UnsupportedEncodingException {
		log.info("Feito Get no webhook " + crcToken);
		return HMAC.calcHmacSha256(oauthProperties.getConsumersecret().getBytes("UTF-8"), crcToken.getBytes("UTF-8"));		
	}	

}
