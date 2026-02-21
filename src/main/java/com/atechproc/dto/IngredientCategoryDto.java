package com.atechproc.dto;

import com.atechproc.model.Food;
import com.atechproc.model.FoodCategory;
import lombok.Data;

import java.util.List;

@Data
public class IngredientCategoryDto {
    private Long id;
    private String name;
    private List<IngredientsItemDto> ingredients;
}
