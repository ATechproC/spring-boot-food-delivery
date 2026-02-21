package com.atechproc.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RestaurantAddressDto {
    private Long id;
    private String streetAddress;
    private String stateProvince;
    private String city;
    private String country;
}
