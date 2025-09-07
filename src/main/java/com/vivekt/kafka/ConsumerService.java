package com.vivekt.kafka;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

@Service
public class ConsumerService {

    private final KafkaConsumer<String, String> consumer;

    public ConsumerService(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    @PostConstruct
    public void startConsumer() {
        new Thread(() -> {
            consumer.subscribe(Collections.singletonList("my-topic"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                records.forEach(record -> {
                    System.out.printf("Consumed message key=%s value=%s partition=%d offset=%d%n",
                            record.key(), record.value(), record.partition(), record.offset());
                });
            }
        }).start();
    }
}
