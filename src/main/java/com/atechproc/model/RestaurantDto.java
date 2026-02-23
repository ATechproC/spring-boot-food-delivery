package com.atechproc.model;
import lombok.*;

import java.util.List;

@Data
public class RestaurantDto {
    private Long id;
    private String title;
    private String description;
    private List<String> images;
}
