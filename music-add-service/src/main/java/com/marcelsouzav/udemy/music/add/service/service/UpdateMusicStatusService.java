package com.marcelsouzav.udemy.music.add.service.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelsouzav.udemy.music.add.service.domain.Music;
import com.marcelsouzav.udemy.music.add.service.gateway.json.MusicJson;
import com.marcelsouzav.udemy.music.add.service.gateway.repository.MusicRepository;
import com.marcelsouzav.udemy.music.add.service.enums.MusicStatusEnums;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateMusicStatusService {

    @Autowired
    private MusicRepository musicRepository;

    @KafkaListener(topics = "${kafka.topic.music-status}", groupId = "${kafka.consumergroup}")
    public void execute(Object musicUpdate) throws JsonProcessingException {

        ConsumerRecord musicConsumer = (ConsumerRecord) musicUpdate;

        String json = (String) musicConsumer.value();

        ObjectMapper mapper = new ObjectMapper();

        MusicJson musicUpdateStatusJson = mapper.readValue(json, MusicJson.class);

        Optional<Music> music = musicRepository.findById(UUID.fromString(musicUpdateStatusJson.getUuid()));

        music.get().setStatus(MusicStatusEnums.READY.toString());
        music.get().setPath(musicUpdateStatusJson.getPath());
        music.get().setSize(musicUpdateStatusJson.getSize());

        musicRepository.save(music.get());
    }

}