package com.matera.eod.admin.broadcast.repository;


import com.matera.eod.admin.broadcast.model.BroadcastEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BroadcastEventRepository extends JpaRepository<BroadcastEventEntity, UUID> {

}
