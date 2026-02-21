package com.atechproc.repository;

import com.atechproc.model.Restaurant;
import com.atechproc.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResRepository extends JpaRepository<Restaurant, Long> {

    @Query("select r from Restaurant r where" +
            " lower(r.name) like lower(concat('%', :keyword, '%')) or " +
            "lower(r.description) like lower(concat('%', :keyword, '%'))")
    List<Restaurant> searchResQuery(@Param("keyword") String keyword);

    Restaurant findByOwner_id(Long userId);

    Restaurant findByName(String name);

    Restaurant findByOwner(User owner);
}
