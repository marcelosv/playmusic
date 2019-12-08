package com.marcelsouzav.udemy.listener.music.api.service;


import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class S3Service {

    public static final String MP3 = ".mp3";

    @Value("${s3.access.key}")
    private String accessKey;

    @Value("${s3.secret.key}")
    private String secretKey;

    @Value("${s3.host}")
    private String s3Host;

    @Value("${s3.bucket}")
    private String s3Bucket;

    public S3ObjectInputStream execute(String path) throws IOException {

        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );

        AmazonS3Client newClient = new AmazonS3Client(credentials,
                new ClientConfiguration().withSignerOverride("S3SignerType"));

        newClient.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
        newClient.setEndpoint(s3Host);

        S3Object object = newClient.getObject(s3Bucket, path);

        return object.getObjectContent();
    }

}
