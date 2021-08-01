package br.com.checkfakebot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
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
	
	public byte[] getImageBytes(String url) {
		byte[] imageByte = restTemplate.getForObject(url, byte[].class);
		return imageByte;
	}
	
	public String getNomeByUrl(String url) {
		String[] urlArray = url.split("/");
		String lastPath = urlArray[urlArray.length-1];
		return lastPath;
	}
	
	public MemeDTO salvarMeme(byte[] arquivo, String nome) {

		String serverUrl = appProperties.getUrlservice() + "/salvar?nome=" + nome;

		MemeDTO memeDTO = postFile(nome,arquivo,serverUrl);
		
		
		return memeDTO;
	}
	
	public MemeDTO postFile(String filename, byte[] someByteArray, String serverUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(filename)
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(someByteArray, fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        
        ResponseEntity<MemeDTO> response = restTemplate.exchange(
        		serverUrl,
                HttpMethod.POST,
                requestEntity,
                MemeDTO.class);
        return response.getBody();

    }
	
	
	

}
