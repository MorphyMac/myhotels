package com.epam.myhotels.api.users.entity;

import com.epam.myhotels.api.users.configs.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PrePersist;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class UserEntityListener {

    @PrePersist
    public void beforeSavingUser(User user) {
        log.debug("user entity listener invoked.");
        if (user == null) throw new IllegalArgumentException("User cannot be NULL while saving");

        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(SpringContextUtil.getBean(PasswordEncoder.class).encode(user.getPassword()));

        if (user.getMemberSince() == null) {
            log.debug("Setting membership date for user.");
            user.setMemberSince(new Date());
        }
    }
}