package com.atechproc.request.res;

import com.atechproc.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class CreateRestaurantRequest {

    @NotBlank(message = "Restaurant name is required")
    private String name;

    @NotBlank(message = "Restaurant description is required")
    private String description;

    @NotBlank(message = "Restaurant title is required")
    private String title;

    @NotBlank(message = "Restaurant cuisine type is required")
    private String cuisineType;

    @NotNull(message = "Restaurant Address is required")
    private RestaurantAddress address;

    @NotNull(message = "Restaurant Contact info is required")
    private ContactInfo contactInformation;

    @NotNull(message = "Restaurant opening hours is required")
    private String openingHours;

    @NotNull(message = "Restaurant num rating is required")
    private int numRating;

    private List<String> images;

    @NotNull(message = "Restaurant registration date is required")
    private String registrationDate;
}
