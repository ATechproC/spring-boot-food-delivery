package com.atechproc.mapper;

import com.atechproc.dto.IngredientCategoryDto;
import com.atechproc.model.IngredientCategory;

import java.util.List;

public class IngredientCategoryMapper {
    public static IngredientCategoryDto toDto(IngredientCategory category) {
        IngredientCategoryDto dto = new IngredientCategoryDto();

        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setIngredients(IngredientsItemMapper.toDTOs(category.getIngredients()));

        return dto;
    }

    public static List<IngredientCategoryDto> toDTOs(List<IngredientCategory> items) {
        return items.stream()
                .map(IngredientCategoryMapper::toDto).toList();
    }
}
