package com.atechproc.service.category;

import com.atechproc.dto.CategoryDto;
import com.atechproc.model.FoodCategory;
import com.atechproc.request.category.CreateCategoryRequest;
import com.atechproc.request.category.UpdateCategoryRequest;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface ICategoryService {
    CategoryDto createFoodCategory(CreateCategoryRequest request, String jwt);
    List<CategoryDto> getFoodCategoryByRestaurantId(String jwt);
    FoodCategory getFoodCategoryById(Long id);
    CategoryDto updateCategoryName(Long id,UpdateCategoryRequest request, String jwt) throws BadRequestException;
    void deleteCategory(Long id, String jwt) throws BadRequestException;
}
