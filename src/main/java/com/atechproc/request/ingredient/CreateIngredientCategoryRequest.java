package com.atechproc.request.ingredient;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateIngredientCategoryRequest {

    @NotBlank(message = "Ingredient category name is required")
    private String name;
}
