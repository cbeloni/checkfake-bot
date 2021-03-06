package br.com.checkfakebot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.checkfakebot.services.TwitterServices;

@RestController
@RequestMapping(value = "twitter")
public class TwitterResource {
	
	@Autowired
	private TwitterServices twitterServices;
	
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
}
