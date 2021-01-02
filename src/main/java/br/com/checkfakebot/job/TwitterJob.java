package br.com.checkfakebot.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.checkfakebot.services.TwitterServices;

@Component
public class TwitterJob {
	
	private static final int FIVE_MINUTES = 5 * 60 * 1000;
	
	@Autowired
	private TwitterServices twitterServices;
	
	@Scheduled(fixedDelay = FIVE_MINUTES) 
    public void verificaMentions() { 
		twitterServices.replyTweetLastFiveminutes();
    }

}
