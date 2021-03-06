package com.epam.myhotels.hotels.model.mapper;

import com.epam.myhotels.hotels.entity.Room;
import com.epam.myhotels.hotels.model.RoomModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoomTypeModelMapper.class})
public interface RoomModelMapper {

    RoomModel toModel(Room room);

    Room toEntity(RoomModel roomModel);

    List<RoomModel> toModels(List<Room> rooms);
}