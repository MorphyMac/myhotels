package com.epam.myhotels.api.users.modelmapper;

import com.epam.myhotels.api.users.entity.User;
import com.epam.myhotels.api.users.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserModelMapper {

    UserModel toModel(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserModel userModel);

    List<UserModel> toModels(List<User> users);
}