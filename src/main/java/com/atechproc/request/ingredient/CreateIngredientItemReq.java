package com.atechproc.request.ingredient;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateIngredientItemReq {
    @NotBlank(message = "Ingredient item name is required")
    private String name;
}
