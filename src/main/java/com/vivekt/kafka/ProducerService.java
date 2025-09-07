package com.vivekt.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final KafkaProducer<String, String> producer;

    public ProducerService(KafkaProducer<String, String> producer) {
        this.producer = producer;
    }

    public void sendMessage(String topic, String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.printf("Message sent to topic=%s partition=%d offset=%d%n",
                        metadata.topic(), metadata.partition(), metadata.offset());
            } else {
                exception.printStackTrace();
            }
        });
    }
}
