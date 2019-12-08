package com.marcelsouzav.udemy.music.add.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelsouzav.udemy.music.add.service.domain.Music;
import com.marcelsouzav.udemy.music.add.service.enums.MusicStatusEnums;
import com.marcelsouzav.udemy.music.add.service.gateway.json.MusicJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateMusicListener {

    @Autowired
    private CreateMusicService createMusicService;

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public String listen(String json) throws InterruptedException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        MusicJson musicJson = mapper.readValue(json, MusicJson.class);

        UUID uuid = createMusicService.execute(Music
                .builder()
                .name(musicJson.getName())
                .status(MusicStatusEnums.WAIT_SAVE_PATH.toString())
                .uuidCustomer(musicJson.getUuidCustomer())
                .build()
        );

        musicJson.setUuid(uuid.toString());

        return mapper.writeValueAsString(musicJson);
    }

}