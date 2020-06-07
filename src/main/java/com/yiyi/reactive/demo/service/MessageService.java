package com.yiyi.reactive.demo.service;

import com.yiyi.reactive.demo.model.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MessageService {

    private ConcurrentLinkedQueue<Message> queue;

    public Flux<Message> streamMessages() {

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux<Message> message = Flux.fromIterable(queue);

        return Flux.zip(message, interval, (key, value) -> key);

    }

    public void addNewMessage() {
        Message message = new Message();
        message.setFrom("1");
        message.setTo("2");
        message.setMessage("skgkshg");
    }


}
