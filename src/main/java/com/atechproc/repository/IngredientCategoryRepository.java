package com.atechproc.repository;

import com.atechproc.model.IngredientCategory;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
    IngredientCategory findByName(String name);

    List<IngredientCategory> findByRestaurantId(Long id);
}
