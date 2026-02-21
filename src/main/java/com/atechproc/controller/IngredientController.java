package com.atechproc.controller;

import com.atechproc.dto.IngredientCategoryDto;
import com.atechproc.dto.IngredientsItemDto;
import com.atechproc.mapper.IngredientCategoryMapper;
import com.atechproc.mapper.IngredientsItemMapper;
import com.atechproc.model.IngredientCategory;
import com.atechproc.model.IngredientsItem;
import com.atechproc.request.ingredient.CreateIngredientCategoryRequest;
import com.atechproc.request.ingredient.CreateIngredientItemReq;
import com.atechproc.response.ApiResponse;
import com.atechproc.service.ingredients.IIngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class IngredientController {

    private final IIngredientService ingredientService;

    @PostMapping("/ingredients/create-ingredient-category")
    public ResponseEntity<ApiResponse> createIngredientCategoryHandler(
            @Valid
            @RequestBody
            CreateIngredientCategoryRequest request,
            @RequestHeader("Authorization") String jwt
    ) {
        IngredientCategoryDto categoryDto = ingredientService.createIngredientCategory(
                request, jwt
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Ingredient category created successfully", categoryDto));
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<ApiResponse> getIngredientCategoryByIdHandler(@PathVariable Long id) {
        IngredientCategory category = ingredientService.getIngredientCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("Success", IngredientCategoryMapper.toDto(category)));
    }

    @GetMapping("/ingredients/restaurant")
    public ResponseEntity<ApiResponse> getIngredientsCategoryByResIdHandler(
            @RequestHeader("Authorization") String jwt
    ) {
        List<IngredientCategoryDto> categories = ingredientService.getIngredientsCategoryByResId(jwt);
        return ResponseEntity.ok(new ApiResponse("Success", categories));
    }

    @PostMapping("/ingredient-items/create-ingredient-item")
    public ResponseEntity<ApiResponse> createIngredientItemHandler(
            @Valid
            @RequestBody
            CreateIngredientItemReq request,
            @RequestParam Long categoryId,
            @RequestHeader("Authorization") String jwt
    ) {
        IngredientsItemDto item = ingredientService.createIngredientItem(
                request, categoryId, jwt
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Ingredient item created successfully", item));
    }

    @GetMapping("/ingredient-items/restaurant")
    public ResponseEntity<ApiResponse> getIngredientsItemsHandler(
             @RequestHeader("Authorization") String jwt
    ) {
        List<IngredientsItemDto> items = ingredientService.getIngredientsItems(jwt);
        return ResponseEntity.ok(new ApiResponse("Success", items));
    }

    @PutMapping("/ingredient-items/update-stock/{itemId}")
    public ResponseEntity<ApiResponse> updateIngredientItemStockHandler(
            @PathVariable Long itemId,
            @RequestParam Long categoryId
    ) throws Exception {
        IngredientsItemDto item = ingredientService.updateIngredientItemStock(itemId, categoryId);
        return ResponseEntity.ok(new ApiResponse("Success", item));
    }

    @GetMapping("/ingredient-items/{itemId}")
    public ResponseEntity<ApiResponse> getIngredientItemByIdHandler(
            @PathVariable Long itemId
    ) throws Exception {
        IngredientsItem item = ingredientService.getIngredientItemById(itemId);
        return ResponseEntity.ok(new ApiResponse("Success", IngredientsItemMapper.toDto(item)));
    }

}
