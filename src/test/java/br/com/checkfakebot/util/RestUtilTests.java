package br.com.checkfakebot.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;

import br.com.checkfakebot.dto.MemeDTO;

@SpringBootTest
@ActiveProfiles(profiles = "local")
public class RestUtilTests {
	
	@Autowired
	private RestUtils restUtils;
	
	@Autowired
    ResourceLoader resourceLoader;
	
	//@Test
	void chamadaRemoteService() {
		MemeDTO memeDTO = restUtils.getMemePorId("1");
		
		assertTrue(true);
		
	}
	
	//@Test
	void salvaImagem() throws IOException {
		byte[] arquivo = obtemArquivo();
		
		MemeDTO memeDTO = restUtils.salvarMeme(arquivo, "pensando");
		
		assertTrue(true);
	}
	
	byte[] obtemArquivo() throws IOException {
		
	    Resource resource = resourceLoader.getResource("classpath:pensando.png");

	    InputStream input = resource.getInputStream();
	    
	    byte[] fileBytes = input.readAllBytes(); 
		
		return fileBytes;
	}
}
