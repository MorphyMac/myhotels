package com.epam.myhotels.api.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements Serializable {

  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}