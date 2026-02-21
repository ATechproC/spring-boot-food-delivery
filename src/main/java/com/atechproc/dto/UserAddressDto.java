package com.atechproc.dto;

import lombok.Data;

@Data
public class UserAddressDto {
    private Long id;
    private String streetAddress;
    private String stateProvince;
    private String postalCode;
    private String country;
}
