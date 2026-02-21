package com.atechproc.repository;

import com.atechproc.model.IngredientsItem;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemsRepository extends JpaRepository<IngredientsItem, Long> {
    IngredientsItem findByName(String name);

    List<IngredientsItem> findByRestaurant_id(Long id);
}
