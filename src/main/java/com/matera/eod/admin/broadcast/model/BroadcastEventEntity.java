package com.matera.eod.admin.broadcast.model;

import com.matera.dtw.commons.cdc.PollingTarget;
import com.matera.dtw.commons.cdc.listener.PollingTargetListener;
import com.matera.dtw.commons.hibernate.type.StringRepresentationUUIDType;
import com.matera.dtw.commons.observability.trace.metadata.TraceMetaData;
import com.matera.dtw.commons.observability.trace.metadata.TraceMetaDataHolder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Entidade JPA que representa o evento de broadcast persistido no banco de dados.
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@Table(name = "BROADCAST_EVENT")
@PollingTarget("ITEM_POLLING_ENTRY")
@EntityListeners({PollingTargetListener.class})
public class BroadcastEventEntity implements TraceMetaDataHolder {

    @Id
    @GeneratedValue(generator = "uuid_generator")
    @Type(StringRepresentationUUIDType.class)
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "EVENT_CODE", nullable = false)
    private String eventCode;

    @Column(name = "EVENT_PARAMETERS", nullable = false)
    private String eventParameters;

    @Column(name = "EVENT_DESCRIPTION", nullable = false)
    private String eventDescription;

    @Column(name = "EVENT_TIME")
    private Long eventTime;

    public BroadcastEventEntity() {
        this.id = UUID.randomUUID();
    }

    @Transient
    @Builder.Default
    private TraceMetaData traceMetaData = TraceMetaData.empty();

    @Override
    public @NotNull TraceMetaData getTraceMetaData() {
        return traceMetaData != null ? traceMetaData : TraceMetaData.empty();
    }
}
