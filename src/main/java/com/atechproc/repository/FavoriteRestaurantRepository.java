package com.atechproc.repository;

import com.atechproc.model.FavoriteRestaurant;
import com.atechproc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRestaurantRepository extends JpaRepository<FavoriteRestaurant, Long> {

    Optional<FavoriteRestaurant> findByTitleAndDescriptionAndUser(String title, String description, User owner);
}
