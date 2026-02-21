package com.atechproc.mapper;

import com.atechproc.dto.CategoryDto;
import com.atechproc.model.FoodCategory;

import java.util.List;

public class CategoryMapper {
    public static CategoryDto toDto(FoodCategory category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setFood(FoodMapper.toDTOs(category.getFood()));

        return categoryDto;
    }

    public static List<CategoryDto> toDTOs(List<FoodCategory> categories) {
        return categories.stream()
                .map(CategoryMapper::toDto).toList();
    }
}
