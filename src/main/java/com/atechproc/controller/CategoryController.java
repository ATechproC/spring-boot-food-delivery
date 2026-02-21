package com.atechproc.controller;

import com.atechproc.dto.CategoryDto;
import com.atechproc.mapper.CategoryMapper;
import com.atechproc.model.FoodCategory;
import com.atechproc.request.category.CreateCategoryRequest;
import com.atechproc.request.category.UpdateCategoryRequest;
import com.atechproc.response.ApiResponse;
import com.atechproc.service.category.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategoryHandler(
            @Valid
            @RequestBody
            CreateCategoryRequest request,
            @RequestHeader("Authorization") String jwt
    ) {
        CategoryDto category = categoryService.createFoodCategory(request, jwt);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Category created successfully", category));
    }

    @GetMapping("/restaurant")
    public ResponseEntity<ApiResponse> getCategoriesByResIdHandler(
            @RequestHeader("Authorization") String jwt
    ) {
        List<CategoryDto> categories = categoryService.getFoodCategoryByRestaurantId(jwt);
        return ResponseEntity.ok(new ApiResponse("Success", categories));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategoryNameHandler(
            @PathVariable Long id,
            @Valid
            @RequestBody
            UpdateCategoryRequest request,
            @RequestHeader("Authorization") String jwt
    ) throws BadRequestException {
        CategoryDto category = categoryService.updateCategoryName(id, request, jwt);
        return ResponseEntity.ok(new ApiResponse("Category name updated successfully", category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryByIdHandler(
            @PathVariable Long id
    ) {
        FoodCategory category = categoryService.getFoodCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("Success", CategoryMapper.toDto(category)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryHandler(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws BadRequestException {
        categoryService.deleteCategory(id, jwt);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));
    }

}
