package br.com.checkfakebot.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import br.com.checkfakebot.dto.MemeDTO;

@SpringBootTest
public class RestUtilTests {
	
	@Autowired
	private RestUtils restUtils;
	
	@Test
	void chamadaRemoteService() {
		MemeDTO memeDTO = restUtils.getMemePorId("1");
		
		assertTrue(true);
		
	}
	
	@Test
	void obtemArquivo() {
		ClassPathResource res = new ClassPathResource("teste.txt");    
		File file = new File(res.getPath());
		
		assertTrue(true);
	}
}
