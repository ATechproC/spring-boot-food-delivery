package com.atechproc.repository;

import com.atechproc.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

    FoodCategory findByName(String name);
}
