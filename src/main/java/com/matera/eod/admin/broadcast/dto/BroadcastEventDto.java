package com.matera.eod.admin.broadcast.dto;

import com.matera.dtw.commons.observability.trace.metadata.TraceMetaData;
import lombok.Data;

import java.util.UUID;

/**
 * Classe DTO que representa os dados do evento de broadcast.
 */
@Data
public class BroadcastEventDto {
    private UUID id;
    private String eventCode;
    private String eventParameters;
    private String eventDescription;
    private long eventTime;
    private TraceMetaData traceMetaData;
}
