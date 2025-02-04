package com.neu.webapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
 
@Configuration
public class AmazonS3Config {
 
    // Access key id will be read from the application.properties file during the application intialization.
//    @Value("${aws.access.key.id}")
//    private String accessKeyId;
//    // Secret access key will be read from the application.properties file during the application intialization.
//    @Value("${aws.access.key.secret}")
//    private String secretAccessKey;
//    // Region will be read from the application.properties file  during the application intialization.
    @Value("${aws.region}")
    private String region;
 
    @Bean
    public AmazonS3 getAmazonS3Cient() {
        //final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        // Get AmazonS3 client and return the s3Client object.
    	//DefaultAWSCredentialsProviderChain defaultAWScreds=new DefaultAWSCredentialsProviderChain();
//    	return AmazonS3ClientBuilder
//                .standard()
//                .withRegion(Regions.fromName(region))
//                .withCredentials(new AWSStaticCredentialsProvider(defaultAWScreds))
//                .build();
    	return AmazonS3ClientBuilder.defaultClient();
    }
}