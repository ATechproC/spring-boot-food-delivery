package com.atechproc.request.res;

import com.atechproc.model.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class UpdateRestaurantRequest {
    private String name;
    private String description;
    private String title;
    private String cuisineType;
    private RestaurantAddress address;
    private ContactInfo contactInformation;
    private LocalTime openingHours;
    private Integer numRating;
    private List<String> images;
    private LocalDateTime registrationDate;
}
