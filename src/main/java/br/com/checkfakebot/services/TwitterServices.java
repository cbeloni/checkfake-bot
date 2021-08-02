package br.com.checkfakebot.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.checkfakebot.config.OauthProperties;
import br.com.checkfakebot.dto.MemeDTO;
import br.com.checkfakebot.util.RestUtils;
import lombok.extern.slf4j.Slf4j;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

@Service
@Slf4j
public class TwitterServices {
	
	@Autowired
	private OauthProperties oauthProperties;
	
	@Autowired
	private RestUtils restUtils;
	
	private static final int FIVE_MINUTES = 5 * 60 * 1000;
    
    public String getMentions(){
    	Twitter twitter = twitterInstance();
        try {
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getMentionsTimeline();
            
            //List<Status> statuses = twitter.getHomeTimeline();
            log.info("Showing @" + user.getScreenName() + "'s mentions.");
            
            List<String> medias = new ArrayList<String>();
            List<String> msg = new ArrayList<String>();
            for (Status status : statuses) {
                //log.info("@" + status.getUser().getScreenName() + " - " + status.getText());
                msg.add(status.getText());
                //log.info("@" + status.getUser().getScreenName() + " - " + status.getMediaEntities());
                for (MediaEntity mediaEntity : status.getMediaEntities()) {
                    //log.info(mediaEntity.getType() + ": bele" + mediaEntity.getMediaURL());
                    medias.add(mediaEntity.getMediaURL());
                }
            }
            for ( String m : msg){
                log.info("Tweet: " + m);
            }
            for ( String m : medias ) {
                log.info("Media: " + m);
            }

        } catch (TwitterException te) {
            te.printStackTrace();
            log.info("Failed to get timeline: " + te.getMessage());
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
            //log.info("Showing @" + user.getScreenName() + "'s mentions.");
            Query query = new Query(hashtag);
            QueryResult result;
            result = twitter.search(query);
            List<Status> statuses = result.getTweets();
            List<String> medias = new ArrayList<String>();
            List<String> msgs = new ArrayList<String>();
            for (Status status : statuses) {
                //log.info("@" + status.getUser().getScreenName() + " - " + status.getText());
                msgs.add(status.getText());
                //log.info("@" + status.getUser().getScreenName() + " - " + status.getMediaEntities());
                for (MediaEntity mediaEntity : status.getMediaEntities()) {
                    //log.info(mediaEntity.getType() + ": bele" + mediaEntity.getMediaURL());
                    medias.add(mediaEntity.getMediaURL());
                }
            }
            for ( String m : msgs){
                log.info("Tweet: " + m);
            }
            for ( String m : medias ) {
                log.info("Media: " + m);
            }
            return msgs;

        } catch (TwitterException te) {
            te.printStackTrace();
            log.info("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
        
        return new ArrayList<>();
    }

    public String tweet(String mensagem){
    	Twitter twitter = twitterInstance();
        try {
            //User user = twitter.verifyCredentials();
            //List<Status> statuses = twitter.getMentionsTimeline();
            //List<Status> statuses = twitter.getHomeTimeline();
            //log.info("Showing @" + user.getScreenName() + "'s mentions.");
            
            Status status = twitter.updateStatus(mensagem);
            log.info("Atualizado com sucesso [" + status.getText() + "].");
            return status.getText();

        } catch (TwitterException te) {
            te.printStackTrace();
            log.info("Failed to get timeline: " + te.getMessage());
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
            log.info("Showing @" + user.getScreenName() + "'s mentions.");
            
            
            Status ultimaMentionStatus = statuses.stream().findFirst().get();
            log.info("Tweet: " + ultimaMentionStatus.getText());

            StatusUpdate stat= new StatusUpdate(mensagem);
            stat.setInReplyToStatusId(ultimaMentionStatus.getInReplyToUserId());

            twitter.updateStatus(stat);

        } catch (TwitterException te) {
            te.printStackTrace();
            log.info("Failed to get timeline: " + te.getMessage());
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
            
            log.info("Showing @" + user.getScreenName() + "'s mentions.");
            
            
            Status ultimaMentionStatus = statuses.stream().findFirst().get();
            log.info("Tweet: " + ultimaMentionStatus.getText());
            mensagemRetorno = "Bot em construção!";
                        
            Date fiveAgo = new Date(new Date().getTime() - FIVE_MINUTES) ; 
            if (ultimaMentionStatus.getCreatedAt().after(fiveAgo)) {
            	log.info("Obtendo imagems");
            	MediaEntity[] mediaEntities = ultimaMentionStatus.getMediaEntities();            	
            	byte[] imageByte = this.restUtils.getImageBytes(mediaEntities[0].getMediaURL());
            	log.info("Salvando meme");
            	String nomeImagem = this.restUtils.getNomeByUrl(mediaEntities[0].getMediaURL());
            	MemeDTO memeDTO = this.restUtils.salvarMeme(imageByte,nomeImagem);
            	mensagemRetorno = "Não tenho certeza, na dúvida, não acredite. Imagem salva: " + memeDTO.getId();
            	StatusUpdate stat= new StatusUpdate(" @" + ultimaMentionStatus.getUser().getScreenName() + 
                        " " + mensagemRetorno);
            	log.info("Atualizando status, mensagem: " + mensagemRetorno);
            	stat.setInReplyToStatusId(ultimaMentionStatus.getId());

                twitter.updateStatus(stat);
            }
            
            mensagem = "Mensagem respondida: " + ultimaMentionStatus.getText() +
            		  " resposta: " + mensagemRetorno ;

        } catch (TwitterException te) {
            te.printStackTrace();
            log.info("Failed to get timeline: " + te.getMessage());
        }
        
        return mensagem;
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
