package com.atechproc.service.ingredients;

import com.atechproc.dto.IngredientCategoryDto;
import com.atechproc.dto.IngredientsItemDto;
import com.atechproc.exception.AlreadyExistsException;
import com.atechproc.exception.ResourceNotFoundException;
import com.atechproc.mapper.IngredientCategoryMapper;
import com.atechproc.mapper.IngredientsItemMapper;
import com.atechproc.model.*;
import com.atechproc.repository.IngredientCategoryRepository;
import com.atechproc.repository.IngredientItemsRepository;
import com.atechproc.request.ingredient.CreateIngredientCategoryRequest;
import com.atechproc.request.ingredient.CreateIngredientItemReq;
import com.atechproc.service.food.IFoodService;
import com.atechproc.service.res.IResService;
import com.atechproc.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IngredientService implements IIngredientService {

    private final IngredientCategoryRepository categoryRepository;
    private final IngredientItemsRepository itemsRepository;
    private final IUserService userService;
    private final IResService resService;
    private final IFoodService foodService;

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public IngredientCategoryDto createIngredientCategory(CreateIngredientCategoryRequest request, String jwt) {
        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());

        IngredientCategory existingCategory = categoryRepository.findByName(request.getName());

        if(existingCategory != null) {
            throw new AlreadyExistsException("Ingredient category already exists");
        }

        IngredientCategory category = new IngredientCategory();
        category.setName(request.getName());
        category.setRestaurant(res);

        IngredientCategory savedCategory = categoryRepository.save(category);

        return IngredientCategoryMapper.toDto(category);
    }

    @Override
    public IngredientCategory getIngredientCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient category not found"));
    }

    @Override
    public List<IngredientCategoryDto> getIngredientsCategoryByResId(String jwt) {

        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());

        List<IngredientCategory> categories = categoryRepository.findByRestaurantId(res.getId());
        return IngredientCategoryMapper.toDTOs(categories);
    }

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public IngredientsItemDto createIngredientItem(CreateIngredientItemReq request, Long categoryId, String jwt) {

        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());

        IngredientCategory category = getIngredientCategoryById(categoryId);

        IngredientsItem existingItem = itemsRepository.findByName(request.getName());

        if(category.getIngredients().contains(existingItem)) {
            throw new AlreadyExistsException("ingredient item already exists in this category.");
        }

        IngredientsItem item = new IngredientsItem();
        item.setName(request.getName());
        item.setRestaurant(res);
        item.setCategory(category);

        IngredientsItem savedItem = itemsRepository.save(item);

        return IngredientsItemMapper.toDto(item);
    }

    @Override
    public List<IngredientsItemDto> getIngredientsItems(String jwt) {
        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());
        List<IngredientsItem> items = itemsRepository.findByRestaurant_id(res.getId());
        return IngredientsItemMapper.toDTOs(items);
    }

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public IngredientsItemDto updateIngredientItemStock(Long itemId, Long categoryId)
            throws Exception {
        IngredientsItem item = getIngredientItemById(itemId);

        IngredientCategory category = getIngredientCategoryById(categoryId);

        if(!category.getIngredients().contains(item)) {
            throw new Exception("This ingredient does not belong to this ingredient category");
        }

        item.setIsInStock(!item.getIsInStock());

        IngredientsItem savedItem = itemsRepository.save(item);

        return IngredientsItemMapper.toDto(savedItem);
    }

    @Override
    public IngredientsItem getIngredientItemById(Long id) {
        return itemsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient item does not found"));
    }
}
