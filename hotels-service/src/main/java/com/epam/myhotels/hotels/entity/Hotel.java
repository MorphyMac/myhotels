package com.epam.myhotels.hotels.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @Column(name = "hotel_id")
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private String contact;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    private Set<Room> rooms;

    // images
    // rating
    // details
}