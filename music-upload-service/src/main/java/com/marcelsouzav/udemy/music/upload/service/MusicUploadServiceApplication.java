package com.marcelsouzav.udemy.music.upload.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableKafka
@EnableAsync
public class MusicUploadServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusicUploadServiceApplication.class, args);
    }
}