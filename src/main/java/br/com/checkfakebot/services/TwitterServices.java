package br.com.checkfakebot.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.checkfakebot.config.OauthProperties;
import twitter4j.Location;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterServices {
	
	@Autowired
	private OauthProperties oauthProperties;
	
	private static final int FIVE_MINUTES = 5 * 60 * 1000;
    
    public String getTrends(){

        return "sucesso";
    }

    public String getMentions(){
    	Twitter twitter = twitterInstance();
        try {
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getMentionsTimeline();
            
            //List<Status> statuses = twitter.getHomeTimeline();
            System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
            
            List<String> medias = new ArrayList<String>();
            List<String> msg = new ArrayList<String>();
            for (Status status : statuses) {
                //System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                msg.add(status.getText());
                //System.out.println("@" + status.getUser().getScreenName() + " - " + status.getMediaEntities());
                for (MediaEntity mediaEntity : status.getMediaEntities()) {
                    //System.out.println(mediaEntity.getType() + ": bele" + mediaEntity.getMediaURL());
                    medias.add(mediaEntity.getMediaURL());
                }
            }
            for ( String m : msg){
                System.out.println("Tweet: " + m);
            }
            for ( String m : medias ) {
                System.out.println("Media: " + m);
            }

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
        
        return "sucesso";
    }

    public List<String> getSearch(String hashtag){
    	Twitter twitter = twitterInstance();
        try {
            User user = twitter.verifyCredentials();
            //List<Status> statuses = twitter.getMentionsTimeline();
            //List<Status> statuses = twitter.getHomeTimeline();
            //System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
            Query query = new Query(hashtag);
            QueryResult result;
            result = twitter.search(query);
            List<Status> statuses = result.getTweets();
            List<String> medias = new ArrayList<String>();
            List<String> msgs = new ArrayList<String>();
            for (Status status : statuses) {
                //System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                msgs.add(status.getText());
                //System.out.println("@" + status.getUser().getScreenName() + " - " + status.getMediaEntities());
                for (MediaEntity mediaEntity : status.getMediaEntities()) {
                    //System.out.println(mediaEntity.getType() + ": bele" + mediaEntity.getMediaURL());
                    medias.add(mediaEntity.getMediaURL());
                }
            }
            for ( String m : msgs){
                System.out.println("Tweet: " + m);
            }
            for ( String m : medias ) {
                System.out.println("Media: " + m);
            }
            return msgs;

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
        
        return new ArrayList<>();
    }

    public String tweet(String mensagem){
    	Twitter twitter = twitterInstance();
        try {
            User user = twitter.verifyCredentials();
            //List<Status> statuses = twitter.getMentionsTimeline();
            //List<Status> statuses = twitter.getHomeTimeline();
            //System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
            
            Status status = twitter.updateStatus(mensagem);
            System.out.println("Successfully updated the status to [" + status.getText() + "].");
            return status.getText();

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
        
        return "Erro ao enviar mensagem";
    }

    public String replyTweet(String mensagem){
    	Twitter twitter = twitterInstance();
        try {
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getMentionsTimeline();
            
            //List<Status> statuses = twitter.getHomeTimeline();
            System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
            
            
            Status ultimaMentionStatus = statuses.stream().findFirst().get();
            System.out.println("Tweet: " + ultimaMentionStatus.getText());

            StatusUpdate stat= new StatusUpdate(mensagem);
            stat.setInReplyToStatusId(ultimaMentionStatus.getInReplyToUserId());

            twitter.updateStatus(stat);

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
        
        return "sucesso";
    }
    
    public String replyTweetLastFiveminutes(){
    	
    	Twitter twitter = twitterInstance();
    	String mensagem = "Processando";
    	String mensagemRetorno = "";
        try {
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getMentionsTimeline();
            
            //List<Status> statuses = twitter.getHomeTimeline();
            System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
            
            
            Status ultimaMentionStatus = statuses.stream().findFirst().get();
            System.out.println("Tweet: " + ultimaMentionStatus.getText());
            mensagemRetorno = "Bot em construção!";
            StatusUpdate stat= new StatusUpdate(" @" + ultimaMentionStatus.getUser().getScreenName() + " " + mensagemRetorno);
            stat.setInReplyToStatusId(ultimaMentionStatus.getId());
            
            Date fiveAgo = new Date(new Date().getTime() - FIVE_MINUTES) ; 
            if (ultimaMentionStatus.getCreatedAt().after(fiveAgo)) {
            	String a = "1";        
            }
            twitter.updateStatus(stat);
            
            mensagem = "Mensagem respondida: " + ultimaMentionStatus.getText() +
            		  " resposta: " + mensagemRetorno ;

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
        }
        
        return mensagem;
    }

    public String getCoutries(){
        try {

            Twitter twitter = twitterInstance();
            //Twitter twitter = new TwitterFactory().getInstance();
            ResponseList<Location> locations;
            locations = twitter.getAvailableTrends();
            System.out.println("Showing available trends");
            for (Location location : locations) {
                System.out.println(location.getName() + " (woeid:" + location.getWoeid() + ")");
            }
            return "Sucesso";
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get trends: " + te.getMessage());
            System.exit(-1);
        }
        return "falha";
    }

	private Twitter twitterInstance() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(oauthProperties.getConsumerkey())
		  .setOAuthConsumerSecret(oauthProperties.getConsumersecret())
		  .setOAuthAccessToken(oauthProperties.getAccesstoken())
		  .setOAuthAccessTokenSecret(oauthProperties.getAccesstokensecret());

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter;
	}
}
