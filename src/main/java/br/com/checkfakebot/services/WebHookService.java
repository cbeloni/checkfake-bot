package br.com.checkfakebot.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebHookService {
	
	public void listenerWebhook(Object body) throws JsonGenerationException, JsonMappingException, IOException {
		
		String json = new ObjectMapper().writeValueAsString(body);

		log.info("Objeto recebido: " + json);
	}
	
	public void getWebHook() {
		log.info("Feito Get no webhook " );
	}
	

}
