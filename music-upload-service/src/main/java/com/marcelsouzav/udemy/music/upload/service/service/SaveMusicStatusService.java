package com.marcelsouzav.udemy.music.upload.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelsouzav.udemy.music.upload.service.gateway.json.MusicJson;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SaveMusicStatusService {

    @Autowired
    @Qualifier("templateUpdate")
    private KafkaTemplate template;

    @Value("${kafka.topic.music-status}")
    private String topicMusicStatus;

    public void execute(MusicJson music) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String musicJson = mapper.writeValueAsString(music);

        ProducerRecord<String, String> producerRecord = new ProducerRecord(topicMusicStatus, musicJson, musicJson);
        template.send(producerRecord);
    }

}