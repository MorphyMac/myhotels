package com.epam.myhotels.hotels.dto.mapper;

import com.epam.myhotels.hotels.dto.RoomTypeDto;
import com.epam.myhotels.hotels.model.RoomTypeModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomTypeDtoMapper {

    RoomTypeDto toDto(RoomTypeModel roomType);

    RoomTypeModel toModel(RoomTypeDto roomType);

    List<RoomTypeDto> toDto(List<RoomTypeModel> roomTypes);
}