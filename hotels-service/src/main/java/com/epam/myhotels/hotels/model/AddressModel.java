package com.epam.myhotels.hotels.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AddressModel implements Serializable {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
}