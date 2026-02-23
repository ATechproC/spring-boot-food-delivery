package com.atechproc.repository;

import com.atechproc.model.FoodCategory;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

    FoodCategory findByName(String name);

    List<FoodCategory> findByRestaurant_id(Long id);

    FoodCategory findByNameAndRestaurant_id(String name, Long id);
}
