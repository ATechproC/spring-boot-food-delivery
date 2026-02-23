package com.atechproc.dto;

import com.atechproc.model.IngredientCategory;
import lombok.Data;

@Data
public class IngredientsItemDto {
    private Long id;
    private String name;
    private Boolean isInStock;
    private CostumeIngredientCategoryDto ingredientCategory;
}
