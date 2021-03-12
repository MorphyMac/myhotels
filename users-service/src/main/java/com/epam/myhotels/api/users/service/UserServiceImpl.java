package com.epam.myhotels.api.users.service;

import com.epam.myhotels.api.users.entity.User;
import com.epam.myhotels.api.users.feignclient.HotelsServiceClient;
import com.epam.myhotels.api.users.model.UserModel;
import com.epam.myhotels.api.users.modelmapper.UserModelMapper;
import com.epam.myhotels.api.users.repository.UserRepository;
import com.epam.myhotels.api.users.service.exception.DuplicateUserException;
import com.epam.myhotels.api.users.service.exception.UserNotFoundException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Setter
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserModelMapper userModelMapper;
    @Autowired
    private HotelsServiceClient hotelsServiceClient;

    @Transactional
    @Override
    public UserModel createUser(UserModel userModel) {
        if (userRepository.findByEmail(userModel.getEmail()).isPresent()) {
            throw new DuplicateUserException("User already exists with email: " + userModel.getEmail());
        }
        return userModelMapper.toModel(userRepository.save(userModelMapper.toEntity(userModel)));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                             .map(user -> new org.springframework.security.core.userdetails.User(user.getEmail(), user
                                     .getPassword(), new ArrayList<>()))
                             .orElseThrow(() -> new UserNotFoundException("User not found. email = " + email));
    }

    @Transactional(readOnly = true)
    @Override
    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email).map(user -> userModelMapper.toModel(user))
                             .orElseThrow(() -> new UserNotFoundException("User not found. email = " + email));
    }

    @Transactional(readOnly = true)
    @Override
    public UserModel findByUserId(String userId) {
        log.debug("log with sleuth+zipkin setup.. Before calling guest service.");
        String status = hotelsServiceClient.getStatus();
        log.debug("guests-service status = {}" + status);
        log.debug("log with sleuth+zipkin setup.. After calling guest service.");

        return userRepository.findByUserId(userId).map(user -> userModelMapper.toModel(user))
                             .orElseThrow(() -> new UserNotFoundException("User not found. userId = " + userId));
    }

    @Transactional
    @Override
    public boolean deleteUser(String userId) {
        log.debug("Deleting user. userId = {}", userId);
        return userRepository.findByUserId(userId).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElseThrow(() -> new UserNotFoundException("User not found. userId = " + userId));
    }

    @Transactional
    @Override
    public UserModel update(String userId, UserModel userModel) {
        log.debug("Updating user. userId = {} userModel: {}", userId, userModel);
        return userRepository.findByUserId(userId).map(user -> {
            User updateUser = userModelMapper.toEntity(userModel);
            updateUser.setId(user.getId());
            return userModelMapper.toModel(userRepository.save(updateUser));
        }).orElseThrow(() -> new UserNotFoundException("User not found. userId = " + userId));
    }
}