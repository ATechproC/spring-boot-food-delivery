package com.atechproc.dto;

import lombok.Data;

@Data
public class IngredientsItemDto {
    private Long id;
    private String name;
    private Boolean isInStock;
    private String ingredientCategory;
}
