package br.com.checkfakebot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrcTokenDTO {
	
	private String response_token;

}
