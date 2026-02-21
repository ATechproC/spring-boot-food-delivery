package com.atechproc.repository;

import com.atechproc.model.Food;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Food findByName(String name);

    @Query("select f from Food f " +
            " where lower(f.name) like lower(concat('%', :keyword, '%')) OR " +
            " lower(f.description) like lower(concat('%', :keyword, '%'))")
    List<Food> searchForFoods(@Param("keyword") String keyword);

    List<Food> findByFoodCategory_id(Long categoryId);

    List<Food> findByRestaurant_id(Long id);
}
