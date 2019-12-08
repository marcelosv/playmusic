package com.marcelsouzav.udemy.listener.music.service.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelsouzav.udemy.listener.music.service.domain.Music;
import com.marcelsouzav.udemy.listener.music.service.gateway.json.MusicJson;
import com.marcelsouzav.udemy.listener.music.service.gateway.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetMusicService {

    @Autowired
    private MusicRepository musicRepository;

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public String execute(String json) throws JsonProcessingException {

        json = json.replaceAll("\"\\{", "\\{");
        json = json.replaceAll("\\}\"", "\\}");
        json = json.replace("\\","");

        ObjectMapper mapper = new ObjectMapper();
        MusicJson musicJson = mapper.readValue(json, MusicJson.class);

        Optional<Music> music = musicRepository.findById(UUID.fromString(musicJson.getUuid()));
        MusicJson musicReturn = MusicJson
                .builder()
                .path(music.get().getPath())
                .size(music.get().getSize())
                .build();

        return mapper.writeValueAsString(musicReturn);
    }

}