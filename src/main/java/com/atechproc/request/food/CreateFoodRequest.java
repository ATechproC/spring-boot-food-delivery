package com.atechproc.request.food;

import com.atechproc.model.IngredientsItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class CreateFoodRequest {

    @NotBlank(message = "Food name is required")
    private String name;

    @NotBlank(message = "Food description is required")
    private String description;

    @NotNull(message = "Food price is required")
    @Positive(message = "Food price must greater than 0")
    private BigDecimal price;

    @NotEmpty(message = "At least one image is required")
    private List<@NotBlank String> images;

    @NotNull(message = "At least one Food ingredients is required")
    private List<IngredientsItem> ingredients;

    @NotNull(message = "Food isVegetarian is required")
    private Boolean isVegetarian;

    @NotNull(message = "Food isSeasonal is required")
    private Boolean isSeasonal;
}
