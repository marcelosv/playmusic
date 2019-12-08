package com.marcelsouzav.udemy.music.upload.service.service;

import com.marcelsouzav.udemy.music.upload.service.gateway.json.MusicJson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UploadMusicService {

    public static final String UTF_8 = "UTF-8";

    @Value("${kafka.topic.request-topic}")
    private String requestTopic;

    @Value("${kafka.header.uuid-customer")
    private String headerCustomer;

    @Value("${kafka.header.uuid-music")
    private String headerMusic;

    @Autowired
    private UploadS3Service uploadS3Service;

    @Autowired
    private SaveMusicStatusService saveMusicStatusService;

    @KafkaListener(topics = "${kafka.topic.request-topic}", groupId = "${kafka.consumer.group-id}")
    public void listen(@Payload ConsumerRecord record,
                       @Headers MessageHeaders messageHeaders) throws InterruptedException, IOException {

        byte[] uuidCustomer = (byte[]) messageHeaders.get(headerCustomer);
        byte[] uuidMusic = (byte[]) messageHeaders.get(headerMusic);

        String uuidCustomerStr = new String(uuidCustomer, UTF_8);
        String uuidMusicStr = new String(uuidMusic, UTF_8);

        Bytes aByte = (Bytes) record.value();

        MusicJson music = uploadS3Service.execute(uuidCustomerStr, uuidMusicStr, aByte);

        saveMusicStatusService.execute(music);
    }


}