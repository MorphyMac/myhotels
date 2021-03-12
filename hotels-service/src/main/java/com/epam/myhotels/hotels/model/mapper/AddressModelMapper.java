package com.epam.myhotels.hotels.model.mapper;

import com.epam.myhotels.hotels.entity.Address;
import com.epam.myhotels.hotels.model.AddressModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressModelMapper {

    AddressModel toModel(Address address);

    Address toEntity(AddressModel addressModel);

    List<AddressModel> toModels(List<Address> addresss);
}