package com.yiyi.reactive.demo.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ITopic;
import com.yiyi.reactive.demo.model.TopcDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {

    private List<String> list = new ArrayList<>();

    private final ReplayProcessor<String> publisher;

    private final HazelcastInstance hazelcastInstance;

    private Map<String, ReplayProcessor<String>> pubs = new HashMap<>();

    private Map<String, ITopic<String>> subs = new HashMap<>();

    private ITopic<TopcDto> topics;

    public MessageController(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
        this.publisher = ReplayProcessor.create(0);
        topics = hazelcastInstance.getTopic("topics");
        topics.addMessageListener(message -> {
            TopcDto topcDto = message.getMessageObject();
            if (pubs.containsKey(topcDto.getName())) {
//                pubs.put(topcDto.getMsg(), ReplayProcessor.create(1));
                pubs.get(topcDto.getName()).onNext(topcDto.getMsg());
            }
        });
    }

    @GetMapping(path = "/stream-flux/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux(@PathVariable String name) {
        if (!pubs.containsKey(name)) {
            ReplayProcessor<String> pub = ReplayProcessor.create(0);

            pubs.put(name, pub);
        }
        if (!subs.containsKey(name)) {
            ITopic<String> topic = hazelcastInstance.getTopic(name);
            topic.addMessageListener(message -> {
                pubs.get(name).onNext(message.getMessageObject());
            });
            subs.put(name, topic);
        }

        return Flux.from(pubs.get(name));
    }

    @GetMapping("/addmessage/{name}")
    public void addMessage(@RequestParam("msg") String msg, @PathVariable String name) {
        topics.publish(new TopcDto(name, msg));
//        if (!subs.containsKey(name)) {
//            ITopic<String> topic = hazelcastInstance.getTopic(name);
//            topic.addMessageListener(message -> {
//               pubs.get(name).onNext(message.getMessageObject());
//            });
//            subs.put(name, topic);
//        } else {
//            System.out.println("get topic" + name);
//        }

//        subs.get(name).publish(msg);
    }
}
