package com.epam.myhotels.reservationservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Date dateIn;
    @Column
    private Date dateOut;

    @Column(nullable = false, updatable = false)
    private Date checkIn;
    @Column(nullable = false)
    private Date checkOut;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationMode mode;

    @Column(nullable = false)
    private Long guestId;

    @Column(name = "room_id", nullable = false, updatable = false)
    private Long roomId;
}