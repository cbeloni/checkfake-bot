package br.com.checkfakebot.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.checkfakebot.dto.MemeDTO;


@RestController("/meme")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemeControler {
	
	@PostMapping("salvar")
    public MemeDTO salvar(@RequestParam("file") MultipartFile file, MemeDTO meme) {
		
        return null;
    }

}
