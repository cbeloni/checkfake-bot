package br.com.checkfakebot.util;

import org.junit.jupiter.api.Test;

public class StringUtilsTests {
	
	@Test
	void montaStringUrl() {		
		String url = "http://xxx.xxx.xx.xx/resources/upload/2014/09/02/new sample.pdf";
		String[] urlArray = url.split("/");
		String lastPath = urlArray[urlArray.length-1];
		System.out.println(lastPath);
	}

}
