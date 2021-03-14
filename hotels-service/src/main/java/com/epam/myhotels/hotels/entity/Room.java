package com.epam.myhotels.hotels.entity;


import com.epam.myhotels.hotels.entity.enums.RoomStatus;
import com.epam.myhotels.hotels.entity.listerner.RoomEntityListener;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@EntityListeners({RoomEntityListener.class})
public class Room implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "number", nullable = false)
    private Integer roomNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {PERSIST, REFRESH, DETACH, MERGE})
    @JoinColumn(name = "room_type_id", updatable = false)
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "hotel_id", insertable = false, updatable = false)
    private Hotel hotel;
    @Column(name = "hotel_id", nullable = false)
    private Long hotelId;

    // price
}