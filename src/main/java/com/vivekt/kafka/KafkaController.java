package com.vivekt.kafka;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final ProducerService producerService;

    public KafkaController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/publish")
    public String publish(@RequestParam String key, @RequestParam String message) {
        producerService.sendMessage("my-topic", key, message);
        return "Message sent!";
    }
}
