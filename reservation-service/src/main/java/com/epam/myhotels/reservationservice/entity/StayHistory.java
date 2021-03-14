package com.epam.myhotels.reservationservice.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stay_history")
public class StayHistory implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long guestId;

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "reservation_id", insertable = false, updatable = false)
    private Reservation reservation;
}