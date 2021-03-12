package com.epam.myhotels.hotels.dto.mapper;

import com.epam.myhotels.hotels.dto.HotelDto;
import com.epam.myhotels.hotels.model.HotelModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressDtoMapper.class, RoomDtoMapper.class})
public interface HotelDtoMapper {

    HotelDto toDto(HotelModel hotel);

    HotelModel toModel(HotelDto hotel);

    List<HotelDto> toDto(List<HotelModel> hotels);
}