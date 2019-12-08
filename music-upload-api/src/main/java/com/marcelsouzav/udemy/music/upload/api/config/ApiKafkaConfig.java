package com.marcelsouzav.udemy.music.upload.api.config;



import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class ApiKafkaConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.topic.requestreply-topic}")
    private String requestReplyTopic;

    @Value("${kafka.consumergroup}")
    private String consumerGroup;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, BytesSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BytesSerializer.class);

        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 30000000);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 30000000);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 30000000);
        props.put(ProducerConfig.SEND_BUFFER_CONFIG, 30000000);
        props.put(ProducerConfig.RECEIVE_BUFFER_CONFIG, 30000000);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000000);

        return props;
    }

    @Bean
    public DefaultKafkaProducerFactory<Object, Object> producerFactory() {
        return new DefaultKafkaProducerFactory(producerConfigs(), new BytesSerializer(), new BytesSerializer());
    }

    @Bean
    public KafkaTemplate<Object, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}