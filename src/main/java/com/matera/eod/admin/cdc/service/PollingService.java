package com.matera.eod.admin.cdc.service;

import com.matera.dtw.commons.cdc.polling.repository.ConsumerBasedPollingEntryRepository;
import com.matera.dtw.commons.cdc.polling.spi.AbstractEntryBasedPollingService;
import com.matera.eod.admin.cdc.dto.PolledEvent;
import com.matera.eod.admin.cdc.repository.PolledEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PollingService extends AbstractEntryBasedPollingService<PolledEvent, PolledEvent> {

    public static final String POLLING_ENTITY_TYPE = "ITEM_POLLING_ENTRY";
    public static final String SERVICE_ID = "polling-service";

    private final PolledEventRepository polledEventRepository;

    @Autowired
    public PollingService(ConsumerBasedPollingEntryRepository repository, PolledEventRepository polledEventRepository) {
        super(SERVICE_ID, POLLING_ENTITY_TYPE);
        this.polledEventRepository = polledEventRepository;
    }


    @Override
    protected List<PolledEvent> extractResultFrom(List<PolledEvent> list) {
        return list;
    }

    @Override
    protected UUID extractEntryFrom(PolledEvent event) {
        return event.getId();
    }

    @Override
    protected List<PolledEvent> doPoll() {
        List<PolledEvent> events = polledEventRepository.findUnprocessedEntries();
        events.forEach(event -> log.info("Polling: Realizando polling do evento: {}", event.getId()));
        return events;
    }
}