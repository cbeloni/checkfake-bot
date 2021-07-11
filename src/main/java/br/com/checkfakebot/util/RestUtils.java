package br.com.checkfakebot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.checkfakebot.config.AppProperties;
import br.com.checkfakebot.dto.MemeDTO;

@Component
public class RestUtils {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AppProperties appProperties;
	
	MemeDTO getMemePorId(String id) {
		ResponseEntity<MemeDTO> memeDTO = restTemplate.getForEntity(appProperties.getUrlservice() + "/obter?id="+ id, MemeDTO.class);
		return memeDTO.getBody();
	}
	

}
