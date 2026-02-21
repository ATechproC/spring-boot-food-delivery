package com.atechproc.service.food;

import com.atechproc.dto.FoodDto;
import com.atechproc.model.Food;
import com.atechproc.request.food.CreateFoodRequest;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IFoodService {
    FoodDto createFood(CreateFoodRequest request, Long categoryId, String jwt);
    void deleteFood(Long Id, String jwt) throws BadRequestException;
    List<FoodDto> searchFood(String keyword);
    Food getFoodById(Long id);
    List<FoodDto> getRestaurantFoods(
            Long resId, Boolean isVegetarian, Boolean Boolean, Long categoryId
    );
    FoodDto updateAvailabilityStatus(Long id, String jwt) throws BadRequestException;
    List<FoodDto> getAllRestaurantFood(String jwt);
}