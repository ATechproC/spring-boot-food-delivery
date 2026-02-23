package com.atechproc.service.category;

import com.atechproc.dto.CategoryDto;
import com.atechproc.exception.AlreadyExistsException;
import com.atechproc.exception.ResourceNotFoundException;
import com.atechproc.mapper.CategoryMapper;
import com.atechproc.model.FoodCategory;
import com.atechproc.model.Restaurant;
import com.atechproc.model.User;
import com.atechproc.repository.FoodCategoryRepository;
import com.atechproc.repository.ResRepository;
import com.atechproc.request.category.CreateCategoryRequest;
import com.atechproc.request.category.UpdateCategoryRequest;
import com.atechproc.service.res.IResService;
import com.atechproc.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final FoodCategoryRepository categoryRepository;
    private final ResRepository resRepository;
    private final IUserService userService;
    private final IResService resService;

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public CategoryDto createFoodCategory(CreateCategoryRequest request, String jwt) {

        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());

        FoodCategory existingCategory = categoryRepository.findByNameAndRestaurant_id(request.getName(), res.getId());

        if(existingCategory != null) {
            throw new AlreadyExistsException("Food category already exists!");
        }

        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setName(request.getName());
        foodCategory.setRestaurant(res);

        FoodCategory savedCategory = categoryRepository.save(foodCategory);

        res.getFoodCategories().add(savedCategory);
        resRepository.save(res);

        return CategoryMapper.toDto(savedCategory);
    }

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public List<CategoryDto> getFoodCategoryByRestaurantId(String jwt) {
        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());
        List<FoodCategory> categories = categoryRepository.findByRestaurant_id(res.getId());
        return CategoryMapper.toDTOs(categories);
    }

    @Override
    public FoodCategory getFoodCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public CategoryDto updateCategoryName(Long id, UpdateCategoryRequest request, String jwt)
            throws BadRequestException {
        FoodCategory existingCategory = getFoodCategoryById(id);

        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());

        if(!res.getFoodCategories().contains(existingCategory)) {
            throw new BadRequestException("Your not allowed to update this category name.");
        }

        existingCategory.setName(request.getName());

        FoodCategory savedCategory = categoryRepository.save(existingCategory);

        return CategoryMapper.toDto(savedCategory);
    }

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public void deleteCategory(Long id, String jwt)
            throws BadRequestException {
        FoodCategory category = getFoodCategoryById(id);

        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());

        if(!res.getFoodCategories().contains(category)) {
            throw new BadRequestException("Your not allowed to delete this category.");
        }

        categoryRepository.deleteById(id);
    }
}
