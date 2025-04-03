package com.matera.eod.admin.mapper;


import com.matera.eod.admin.broadcast.dto.BroadcastEventDto;
import com.matera.eod.admin.broadcast.model.BroadcastEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper para converter entre BroadcastEventEntity e BroadcastEventDto.
 */
@Mapper(componentModel = "spring")
public interface BroadcastEventMapper {

    BroadcastEventMapper INSTANCE = Mappers.getMapper(BroadcastEventMapper.class);

    BroadcastEventDto entityToDto(BroadcastEventEntity entity);

    BroadcastEventEntity dtoToEntity(BroadcastEventDto dto);
}