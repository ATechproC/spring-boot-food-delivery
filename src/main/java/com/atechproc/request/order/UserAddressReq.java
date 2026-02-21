package com.atechproc.request.order;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAddressReq {

    @NotBlank(message = "Street address is required")
    private String streetAddress;

    @NotBlank(message = "State province is required")
    private String stateProvince;

    @NotBlank(message = "Postal code is required")
    private String postalCode;

    @NotBlank(message = "User country is required")
    private String country;
}
