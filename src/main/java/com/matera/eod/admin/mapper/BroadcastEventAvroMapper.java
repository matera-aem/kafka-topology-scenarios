package com.matera.eod.admin.mapper;

import com.matera.eod.admin.broadcast.model.BroadcastEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BroadcastEventAvroMapper {


    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToString")
    @Mapping(target = "eventCode", source = "eventCode")
    @Mapping(target = "eventParameters", source = "eventParameters")
    @Mapping(target = "eventDescription", source = "eventDescription")
    com.matera.eod.admin.broadcast.avro.BroadcastEventAvro entityToAvro(BroadcastEventEntity entity);

    @Named("uuidToString")
    static CharSequence uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }
}
