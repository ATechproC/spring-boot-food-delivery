package com.atechproc.service.food;

import com.atechproc.dto.FoodDto;
import com.atechproc.exception.AlreadyExistsException;
import com.atechproc.exception.ResourceNotFoundException;
import com.atechproc.mapper.FoodMapper;
import com.atechproc.model.Food;
import com.atechproc.model.FoodCategory;
import com.atechproc.model.Restaurant;
import com.atechproc.model.User;
import com.atechproc.repository.FoodRepository;
import com.atechproc.request.food.CreateFoodRequest;
import com.atechproc.service.category.ICategoryService;
import com.atechproc.service.res.IResService;
import com.atechproc.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService implements IFoodService {

    private final FoodRepository foodRepository;
    private final ICategoryService categoryService;
    private final IUserService userService;
    private final IResService resService;

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public FoodDto createFood(CreateFoodRequest request, Long categoryId, String jwt)
             {
        FoodCategory category = categoryService.getFoodCategoryById(categoryId);

        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());

        Food existingFood = foodRepository.findByName(request.getName());

        if(category.getFood().contains(existingFood)) {
            throw new AlreadyExistsException("Food already exists with name " + request.getName());
        }

        Food food = new Food();
        food.setFoodCategory(category);
        food.setImages(request.getImages());
        food.setIsSeasonal(request.getIsSeasonal());
        food.setIsVegetarian(request.getIsVegetarian());
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setRestaurant(res);
        food.setFoodCategory(category);
        food.setIngredients(request.getIngredients());

        Food savedFood = foodRepository.save(food);

        return FoodMapper.toDto(savedFood);
    }

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public void deleteFood(Long id, String jwt) throws BadRequestException {
        Food food = getFoodById(id);

        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());

        if(!res.getFoods().contains(food)) {
            throw new BadRequestException("Bad required : food does not belong to this restaurant");
        }

        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<FoodDto> searchFood(String keyword) {
        List<Food> foods = foodRepository.searchForFoods(keyword);
        return FoodMapper.toDTOs(foods);
    }

    @Override
    public Food getFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));
    }

    @Override
    public List<FoodDto> getRestaurantFoods(
            Long resId,
            Boolean isVegetarian,
            Boolean isSeasonal
    ) {

        List<Food> foods = foodRepository.findByRestaurant_id(resId);

        if(isVegetarian != null && isVegetarian) {
            foods = foods.stream().filter(Food::getIsVegetarian).toList();
        }

        if(isSeasonal != null && isSeasonal) {
            foods = foods.stream().filter(Food::getIsSeasonal).toList();
        }

        return FoodMapper.toDTOs(foods);
    }

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public FoodDto updateAvailabilityStatus(Long id, String jwt)
            throws BadRequestException {
        Food food = getFoodById(id);

        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());

        if(!res.getFoods().contains(food)) {
            throw new BadRequestException("This food does belong to this restaurant!");
        }

        food.setAvailable(!food.getAvailable());

        Food savedFood = foodRepository.save(food);

        return FoodMapper.toDto(savedFood);
    }

    @Override
    public List<FoodDto> getAllRestaurantFood(String jwt) {
        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());
        List<Food> foods = foodRepository.findByRestaurant_id(res.getId());
        return FoodMapper.toDTOs(foods);
    }
    

}
