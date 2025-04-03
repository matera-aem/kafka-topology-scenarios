package com.matera.eod.admin.cdc.config;

import com.matera.dtw.commons.cdc.polling.spi.AbstractPollingConfigurer;
import com.matera.dtw.commons.cdc.polling.spi.Dispatcher;
import com.matera.eod.admin.cdc.dto.PolledEvent;
import com.matera.eod.admin.cdc.service.PollingService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class PollingConfiguration extends AbstractPollingConfigurer<PollingService, PolledEvent, PollingConfiguration> {

    private final PollingService pollingService;
    private final Dispatcher<PolledEvent> dispatcher;

    @Value("${polling.frequency.duration:PT5S}")
    private Duration pollingFrequency;

    @PostConstruct
    public void start() {
        withId("polling-service");
        withFrequency(pollingFrequency);
        pollingFrom(() -> pollingService, "nextEntries");
        extractingLatencyWith(entity -> Duration.ZERO);

    }

    @Override
    protected Dispatcher<PolledEvent> doDispatcher() {
        return dispatcher;
    }
}