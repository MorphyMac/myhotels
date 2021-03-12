package com.epam.myhotels.api.users.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
@EntityListeners({UserEntityListener.class})
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private String userId;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, unique = true, length = 120, updatable = false)
    private String email;
    @Column(nullable = false, unique = true, updatable = false)
    private String password;
    @Column(nullable = false, updatable = false)
    private Date memberSince;
}