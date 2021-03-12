package com.epam.myhotels.hotels.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "room_type")
@Data
@NoArgsConstructor
public class RoomType implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private String description;
}