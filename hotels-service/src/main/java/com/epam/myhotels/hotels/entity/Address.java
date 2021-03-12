package com.epam.myhotels.hotels.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address implements Serializable {
    @NotNull
    @Size(min = 1, max = 50)
    private String addressLine1;
    @Size(max = 50)
    private String addressLine2;
    @NotNull
    @Size(min = 1, max = 50)
    private String city;
    @NotNull
    @Size(max = 127)
    private String state;
    @NotNull
    private String country;
}