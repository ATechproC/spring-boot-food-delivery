package com.atechproc.service.ingredients;

import com.atechproc.dto.IngredientCategoryDto;
import com.atechproc.dto.IngredientsItemDto;
import com.atechproc.model.IngredientCategory;
import com.atechproc.model.IngredientsItem;
import com.atechproc.request.ingredient.CreateIngredientCategoryRequest;
import com.atechproc.request.ingredient.CreateIngredientItemReq;

import java.util.List;

public interface IIngredientService {
    IngredientCategoryDto createIngredientCategory(
            CreateIngredientCategoryRequest request,
            String jwt
    );
    IngredientCategory getIngredientCategoryById(Long id);
    List<IngredientCategoryDto> getIngredientsCategoryByResId(String jwt);

    IngredientsItemDto createIngredientItem(
            CreateIngredientItemReq request,
            Long categoryId,
            String jwt
    );
    List<IngredientsItemDto> getIngredientsItems(String jwt);

    IngredientsItemDto updateIngredientItemStock(Long itemId, Long categoryId) throws Exception;
    IngredientsItem getIngredientItemById(Long id);
}
