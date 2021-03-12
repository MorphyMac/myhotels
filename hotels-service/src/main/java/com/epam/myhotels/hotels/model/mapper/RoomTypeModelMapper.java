package com.epam.myhotels.hotels.model.mapper;

import com.epam.myhotels.hotels.entity.RoomType;
import com.epam.myhotels.hotels.model.RoomTypeModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomTypeModelMapper {

    RoomTypeModel toModel(RoomType roomType);

    RoomType toEntity(RoomTypeModel roomTypeModel);

    List<RoomTypeModel> toModels(List<RoomType> roomTypes);
}