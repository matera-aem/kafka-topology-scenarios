package com.matera.eod.admin.cdc.service;

import com.matera.dtw.commons.cdc.polling.impl.kafka.exception.KafkaTemplateTimeoutSendException;
import com.matera.dtw.commons.cdc.polling.spi.Dispatcher;
import com.matera.eod.admin.broadcast.avro.BroadcastEventAvro;
import com.matera.eod.admin.broadcast.model.BroadcastEventEntity;
import com.matera.eod.admin.broadcast.repository.BroadcastEventRepository;
import com.matera.eod.admin.cdc.dto.PolledEvent;
import com.matera.eod.admin.mapper.BroadcastEventAvroMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DispatcherService implements Dispatcher<PolledEvent> {

    private final KafkaTemplate<String, BroadcastEventAvro> kafkaTemplate;
    private final BroadcastEventRepository broadcastEventRepository;
    private final BroadcastEventAvroMapper broadcastEventAvroMapper;

    @Value("${spring.kafka.producer.topic:broadcast-events}")
    private String topic;

    @Override
    public void dispatch(List<PolledEvent> list) throws KafkaTemplateTimeoutSendException {
        list.forEach(this::processEntry);
    }

    private void processEntry(PolledEvent entry) {
        UUID eventId;
        try {
            eventId = UUID.fromString(String.valueOf(entry.getId()));
        } catch (IllegalArgumentException e) {
            log.error("ID inválido para evento: {}", entry.getId(), e);
            return;
        }

        log.info("Dispatcher: Processando evento com ID={}", eventId);

        // Buscar a entidade no banco para enriquecer o evento
        BroadcastEventEntity eventEntity = broadcastEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado no banco para ID: " + eventId));

        // Converter entidade para Avro
        BroadcastEventAvro eventAvro = broadcastEventAvroMapper.entityToAvro(eventEntity);

        // Garantir que o ID do Avro seja o mesmo do `entry`
        eventAvro.setId(eventId.toString());

        // Enviar evento enriquecido para o Kafka
        kafkaTemplate.send(topic, eventId.toString(), eventAvro)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Evento enriquecido enviado com sucesso para o tópico {}: {}", topic, eventAvro);
                    } else {
                        log.error("Erro ao enviar evento para o Kafka: {}", ex.getMessage(), ex);
                    }
                });
    }
}
