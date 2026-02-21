package com.atechproc.request.food;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateFoodRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> images;
    private Boolean isVegetarian;
    private Boolean isSeasonal;
}
