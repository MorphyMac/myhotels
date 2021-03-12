package com.epam.myhotels.api.users.service;

import com.epam.myhotels.api.users.model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserModel createUser(UserModel userModel);

    UserModel findByEmail(String email);

    UserModel findByUserId(String userId);

    UserModel update(String userId, UserModel userModel);

    boolean deleteUser(String userId);
}