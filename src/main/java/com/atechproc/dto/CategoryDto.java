package com.atechproc.dto;

import com.atechproc.model.Food;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private List<FoodDto> food = new ArrayList<>();
}
