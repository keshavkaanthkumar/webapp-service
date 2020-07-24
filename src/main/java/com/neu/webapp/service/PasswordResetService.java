package com.neu.webapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import com.neu.webapp.repository.UserDao;

@Service
public class PasswordResetService {

	@Autowired
	UserDao userDao;
	//@Value("${cloud.snsTopic}")
	private String snsTopic="password_reset";

	private static String topicArn = "";

	private static final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);

	public void sendEmailToUser(String email) {
		if(userDao.existsById(email)) {
		logger.info("Sending mail to user using topic " + snsTopic);

		topicArn = snsTopic;
		logger.info("Sending mail to user "+email);

		AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();

		final String msg = email;
		logger.info("Message  "+msg);


		List<Topic> topics = snsClient.listTopics().getTopics();
        for(Topic topic: topics)
        {
            if(topic.getTopicArn().endsWith("password_reset")) {

                logger.info("Found the topic password_reset");
                PublishRequest req = new PublishRequest(topic.getTopicArn(),msg);
                snsClient.publish(req);
                break;
            }
            
        }
		}
		else
		{
			throw new RuntimeException("Invalid email id");
		}
	}

}
