package com.atechproc.dto;

import com.atechproc.model.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class ResDto {

    private Long id;

    private String name;

    private String description;

    private String cuisineType;

    private RestaurantAddressDto address;

    private ContactInfo contactInformation;

    private LocalTime openingHours;

    private int numRating;

    private List<String> images;

    private LocalDateTime registrationDate;

    private Boolean open;
}
