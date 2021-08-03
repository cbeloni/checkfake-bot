package br.com.checkfakebot.resources;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.checkfakebot.services.TwitterServices;
import br.com.checkfakebot.services.WebHookService;

@RestController
@RequestMapping(value = "twitter")
public class TwitterResource {
	
	@Autowired
	private TwitterServices twitterServices;
	
	@Autowired
	private WebHookService webHookService;
	
	@GetMapping
	public ResponseEntity<String> getMentions(){
		String resultado = twitterServices.getMentions(); 
		return ResponseEntity.ok(resultado);
	}

	@GetMapping("/hashtag/{hashtag}")
	public ResponseEntity<List<String>> gethashtag(@PathVariable("hashtag") String hashtag){
		List<String> tags = twitterServices.getSearch(hashtag);
		return ResponseEntity.ok(tags);
	}

	@PostMapping("/tweet/{mensagem}")
	public ResponseEntity<String> tweet(@PathVariable("mensagem") String mensagem){
		String resultado = twitterServices.tweet(mensagem);

		return ResponseEntity.ok(resultado);
	}

	@PostMapping("/retweet/{mensagem}")
	public ResponseEntity<String> retweet(@PathVariable("mensagem") String mensagem){
		String resultado = twitterServices.replyTweet(mensagem);

		return ResponseEntity.ok(resultado);
	}
	
	@PostMapping("/retweet/job")
	public ResponseEntity<String> retweet(){
		String resultado = twitterServices.replyTweetLastFiveminutes();

		return ResponseEntity.ok(resultado);
	}
	
	@PostMapping("/webhook")
	public ResponseEntity<String> webhook(@RequestBody Object body) throws JsonGenerationException, JsonMappingException, IOException{
		webHookService.listenerWebhook(body);
		
		return ResponseEntity.ok("processado");
	}
	
	@GetMapping("/webhook")
	public ResponseEntity<String> webhook() {
		webHookService.getWebHook();
		
		return ResponseEntity.ok("processado");
	}
	
	
}
