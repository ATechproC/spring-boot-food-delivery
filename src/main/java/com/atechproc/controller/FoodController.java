package com.atechproc.controller;

import com.atechproc.dto.FoodDto;
import com.atechproc.mapper.FoodMapper;
import com.atechproc.model.Food;
import com.atechproc.request.food.CreateFoodRequest;
import com.atechproc.response.ApiResponse;
import com.atechproc.service.food.IFoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/food")
@RequiredArgsConstructor
public class FoodController {

    private final IFoodService foodService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createFoodHandler(
            @Valid
            @RequestBody
            CreateFoodRequest request,
            @RequestParam("categoryId") Long categoryId,
            @RequestHeader("Authorization") String jwt
    ) {
        FoodDto food = foodService.createFood(request, categoryId, jwt);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Food created successfully", food));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteFoodHandler(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws BadRequestException {
        foodService.deleteFood(id, jwt);
        return ResponseEntity.ok(new ApiResponse("Food deleted successfully", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchForFoodHandler(
            @RequestParam String keyword
    ) throws BadRequestException {
        List<FoodDto> foods = foodService.searchFood(keyword);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Success", foods));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getFoodByHandler(@PathVariable Long id) {
        Food food = foodService.getFoodById(id);
        return ResponseEntity.ok(new ApiResponse("Success", FoodMapper.toDto(food)));
    }

    @GetMapping("/restaurant/{resId}")
    public ResponseEntity<ApiResponse> getRestaurantFoods(
            @PathVariable Long resId,
            @RequestParam(required = false) Boolean isVegetarian,
            @RequestParam(required = false) Boolean isSeasonal,
            @RequestParam Long categoryId
    ) {
        List<FoodDto> foods = foodService.getRestaurantFoods(resId, isVegetarian, isSeasonal, categoryId);
        return ResponseEntity.ok(new ApiResponse("Success", foods));
    }

    @PutMapping("/update-availability/{id}")
    public ResponseEntity<ApiResponse> updateFoodAvailability(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws BadRequestException {
        FoodDto food = foodService.updateAvailabilityStatus(id, jwt);
        return ResponseEntity.ok(new ApiResponse("Food availability changed successfully", food));
    }

    @GetMapping("/restaurant/get-all")
    public ResponseEntity<ApiResponse> getAllFoodRestaurant(
            @RequestHeader("Authorization") String jwt
    ) {
        List<FoodDto> foods = foodService.getAllRestaurantFood(jwt);
        return ResponseEntity.ok(new ApiResponse("Success", foods));
    }

}
