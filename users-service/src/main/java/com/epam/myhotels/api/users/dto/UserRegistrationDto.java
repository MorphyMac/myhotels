package com.epam.myhotels.api.users.dto;

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

    @NotNull(message = "{NotNull.user.firstname}")
    @Size(min = 2, message = "{Size.user.firstname}")
    private String firstName;

    @NotNull(message = "{NotNull.user.lastname}")
    @Size(min = 2, message = "{Size.user.lastname}")
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull(message = "{NotNull.user.password}")
    @Size(min = 8, max = 16, message = "{Size.user.password}")
    private String password;
}