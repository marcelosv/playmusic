package com.marcelsouzav.udemy.listener.music.service;


import com.marcelsouzav.udemy.listener.music.service.domain.Music;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EntityScan(basePackageClasses = Music.class)
@EnableAsync
@EnableKafka
public class MusicListenerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicListenerServiceApplication.class, args);
    }
}