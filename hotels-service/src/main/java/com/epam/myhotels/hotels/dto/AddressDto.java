package com.epam.myhotels.hotels.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AddressDto implements Serializable {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
}