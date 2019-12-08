package com.marcelsouzav.udemy.music.upload.service.service;


import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.marcelsouzav.udemy.music.upload.service.gateway.json.MusicJson;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class UploadS3Service {

    public static final String MP3 = ".mp3";

    @Value("${s3.access.key}")
    private String accessKey;

    @Value("${s3.secret.key}")
    private String secretKey;

    @Value("${s3.host}")
    private String s3Host;

    @Value("${s3.bucket}")
    private String s3Bucket;

    public MusicJson execute(String uuidCustomerStr, String uuidMusicStr, Bytes aByte) throws IOException {

        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );

        AmazonS3Client newClient = new AmazonS3Client(credentials,
                new ClientConfiguration().withSignerOverride("S3SignerType"));

        newClient.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
        newClient.setEndpoint(s3Host);

        String nameFile = createName(uuidCustomerStr, uuidMusicStr);

        File file = new File(nameFile);
        Path path = file.toPath();
        Files.write(path, aByte.get());

        newClient.putObject(s3Bucket, nameFile, path.toFile());

        return MusicJson
                .builder()
                .uuid(uuidMusicStr)
                .path(nameFile)
                .size(aByte.get().length)
                .build();
    }

    private String createName(String uuidCustomer, String uuidMusic) {
        return uuidCustomer.concat("-").concat(uuidMusic).concat(MP3);
    }
}