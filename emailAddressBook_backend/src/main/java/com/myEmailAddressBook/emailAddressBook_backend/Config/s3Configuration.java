package com.myEmailAddressBook.emailAddressBook_backend.Config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class s3Configuration {

    private Logger logger = Logger.getLogger(s3Configuration.class.getName());

    @Value("${spaces.endpoint}")
    private String endpoint;
    
    @Value("${spaces.region}")
    private String region;

    @Bean
    AmazonS3 createS3Client(){

        final String accessKey = "OL4KWLJ25UVJHMTPHC34";
        final String secretKey = "/fUyJLivou2g8lYaBn60Ycn6Sp5n/nmS884jUtipjMw";
        logger.log(Level.INFO, "accessKey > " + accessKey);
        logger.log(Level.INFO, "secretKey > " + secretKey);
        
        // S3 credentials
        final BasicAWSCredentials basiccred = new BasicAWSCredentials(accessKey, secretKey);
        final AWSStaticCredentialsProvider credProv = new AWSStaticCredentialsProvider(basiccred);

        final EndpointConfiguration endpointConfig = new EndpointConfiguration(endpoint, region);

        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(endpointConfig)
            .withCredentials(credProv)
            .build();
    }
    
}
