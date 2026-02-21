package com.atechproc.dto;

import com.atechproc.model.FoodCategory;
import com.atechproc.model.IngredientsItem;
import com.atechproc.model.Restaurant;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class FoodDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> images;
    private Boolean available;
    private Boolean isVegetarian;
    private Boolean isSeasonal;
    private List<IngredientsItemDto> ingredients;
    private LocalDateTime creationDate;
}
