package com.marcelsouzav.udemy.music.add.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableKafka
@EnableAsync
public class MusicApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusicApiApplication.class, args);
    }
}