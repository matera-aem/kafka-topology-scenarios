package com.matera.eod.admin.kafka.consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class BroadcastEventConsumer {

    @KafkaListener(
            topics = "${spring.kafka.producer.topic:broadcast-events}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenBroadcastEvents(
            com.matera.eod.admin.broadcast.avro.BroadcastEventAvro event,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {
        System.out.println("Recebido Avro: " + event);
        System.out.println("Chave da mensagem: " + key);

        System.out.println("ID         = " + event.getId());
        System.out.println("EventCode  = " + event.getEventCode());
        System.out.println("Parameters = " + event.getEventParameters());
        System.out.println("Desc       = " + event.getEventDescription());
    }
}
