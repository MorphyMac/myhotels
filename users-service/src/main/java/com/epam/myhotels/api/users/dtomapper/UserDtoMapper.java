package com.epam.myhotels.api.users.dtomapper;

import com.epam.myhotels.api.users.dto.UserDto;
import com.epam.myhotels.api.users.dto.UserRegistrationDto;
import com.epam.myhotels.api.users.model.UserModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserModel toModel(UserDto userDto);

    UserModel toModel(UserRegistrationDto registrationDto);

    UserDto toDto(UserModel userModel);

    List<UserDto> toDto(List<UserModel> userModels);
}