package com.matera.eod.admin.cdc.repository;

import com.matera.dtw.commons.cdc.entity.SimplePollingEntry;

import com.matera.eod.admin.cdc.dto.PolledEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PolledEventRepository extends JpaRepository<SimplePollingEntry, UUID> {

    @Query("select new com.matera.eod.admin.cdc.dto.PolledEvent(e.id) " +
            "from SimplePollingEntry e where e.processed = false")
    List<PolledEvent> findUnprocessedEntries();
}