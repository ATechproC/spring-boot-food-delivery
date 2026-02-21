package com.atechproc.mapper;

import com.atechproc.dto.FoodDto;
import com.atechproc.model.Food;

import java.util.List;

public class FoodMapper {
    public static FoodDto toDto(Food food) {
        FoodDto foodDto = new FoodDto();
        foodDto.setId(food.getId());
        foodDto.setAvailable(food.getAvailable());
        foodDto.setImages(food.getImages());
        foodDto.setIngredients(IngredientsItemMapper.toDTOs(food.getIngredients()));
        foodDto.setName(food.getName());
        foodDto.setDescription(food.getDescription());
        foodDto.setPrice(food.getPrice());
        foodDto.setCreationDate(food.getCreationDate());
        foodDto.setIsSeasonal(food.getIsSeasonal());
        foodDto.setIsVegetarian(food.getIsVegetarian());

        return foodDto;
    }

    public static List<FoodDto> toDTOs(List<Food> foods) {
        return foods.stream()
                .map(FoodMapper::toDto).toList();
    }
}
