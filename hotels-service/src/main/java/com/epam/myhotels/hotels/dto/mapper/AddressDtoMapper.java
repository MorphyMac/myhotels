package com.epam.myhotels.hotels.dto.mapper;

import com.epam.myhotels.hotels.dto.AddressDto;
import com.epam.myhotels.hotels.model.AddressModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressDtoMapper {

    AddressDto toDto(AddressModel address);

    AddressModel toModel(AddressDto address);

    List<AddressDto> toDto(List<AddressModel> addresses);
}