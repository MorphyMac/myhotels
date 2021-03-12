package com.epam.myhotels.hotels.dto.mapper;

import com.epam.myhotels.hotels.dto.RoomDto;
import com.epam.myhotels.hotels.model.RoomModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoomTypeDtoMapper.class})
public interface RoomDtoMapper {

    RoomDto toDto(RoomModel room);

    RoomModel toModel(RoomDto roomModel);

    List<RoomDto> toDto(List<RoomModel> rooms);
}