package com.matera.eod.admin.broadcast.service;


import com.matera.eod.admin.broadcast.dto.BroadcastEventDto;
import com.matera.eod.admin.broadcast.model.BroadcastEventEntity;
import com.matera.eod.admin.broadcast.repository.BroadcastEventRepository;
import com.matera.eod.admin.mapper.BroadcastEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Serviço que processa o evento de broadcast persistindo a entidade de negócio.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class BroadcastEventService {

    private final BroadcastEventRepository repository;
    private final BroadcastEventMapper mapper;

    public void saveBroadcastEvent(BroadcastEventDto dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID());
        }

        BroadcastEventEntity entity = mapper.dtoToEntity(dto);
        entity = repository.save(entity);
        log.info("Evento salvo com sucesso: ID {}", entity.getId());
        mapper.entityToDto(entity);
    }


}
