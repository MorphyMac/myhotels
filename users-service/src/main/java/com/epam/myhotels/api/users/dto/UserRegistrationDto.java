package com.epam.myhotels.api.users.dto;

import com.epam.myhotels.api.users.dto.UserValidationGroup.CreateUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    @NotNull(message = "{NotNull.user.firstName}", groups = {CreateUser.class})
    @Size(min = 2, message = "{Size.user.firstName}", groups = {CreateUser.class})
    private String firstName;

    @NotNull(message = "{NotNull.user.lastName}", groups = {CreateUser.class})
    @Size(min = 2, message = "{Size.user.lastName}", groups = {CreateUser.class})
    private String lastName;

    @NotNull(groups = {CreateUser.class})
    @Email(groups = {CreateUser.class})
    private String email;

    @NotNull(message = "{NotNull.user.password}", groups = {CreateUser.class})
    @Size(min = 8, max = 16, message = "{Size.user.password}", groups = {CreateUser.class})
    private String password;
}