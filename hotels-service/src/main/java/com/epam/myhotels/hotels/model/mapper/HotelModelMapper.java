package com.epam.myhotels.hotels.model.mapper;

import com.epam.myhotels.hotels.entity.Hotel;
import com.epam.myhotels.hotels.model.HotelModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressModelMapper.class, RoomModelMapper.class})
public interface HotelModelMapper {

    @Mapping(target = "rooms", ignore = true)
    HotelModel toModel(Hotel hotel);

    Hotel toEntity(HotelModel hotelModel);

    List<HotelModel> toModels(List<Hotel> hotels);
}