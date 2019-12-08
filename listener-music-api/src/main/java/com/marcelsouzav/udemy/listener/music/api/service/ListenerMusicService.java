package com.marcelsouzav.udemy.listener.music.api.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelsouzav.udemy.listener.music.api.gateway.json.MusicJson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class ListenerMusicService {

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    private String requestTopic;

    @Value("${kafka.topic.requestreply-topic}")
    private String requestReplyTopic;

    public MusicJson execute(MusicJson musicJson) throws ExecutionException, InterruptedException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(musicJson);

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, json);

        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

        RequestReplyFuture<String, String, String> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();

        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();

        String jsonReturn = consumerRecord.value();
        jsonReturn = jsonReturn.replaceAll("\"\\{", "\\{");
        jsonReturn = jsonReturn.replaceAll("\\}\"", "\\}");
        jsonReturn = jsonReturn.replace("\\","");

        MusicJson musicJsonReturn = mapper.readValue(jsonReturn, MusicJson.class);

        return musicJsonReturn;
    }
}
