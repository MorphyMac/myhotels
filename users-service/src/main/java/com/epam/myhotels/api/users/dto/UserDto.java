package com.epam.myhotels.api.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.epam.myhotels.api.users.dto.UserValidationGroup.CreateUser;
import static com.epam.myhotels.api.users.dto.UserValidationGroup.UpdateUser;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull(message = "{NotNull.user.firstname}", groups = {CreateUser.class, UpdateUser.class})
    @Size(min = 2, message = "{Size.user.firstname}", groups = {CreateUser.class, UpdateUser.class})
    private String firstName;

    @NotNull(message = "{NotNull.user.lastname}", groups = {CreateUser.class, UpdateUser.class})
    @Size(min = 2, message = "{Size.user.lastname}", groups = {CreateUser.class, UpdateUser.class})
    private String lastName;

    @NotNull(groups = CreateUser.class)
    @Email(groups = CreateUser.class)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "{NotNull.user.password}", groups = CreateUser.class)
    @Size(min = 8, max = 16, message = "{Size.user.password}", groups = CreateUser.class)
    private String password;

    private String userId;
}