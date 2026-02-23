package com.atechproc.repository;

import com.atechproc.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByRestaurant_id(Long id);

    List<Order> findByCustomer_id(Long id);
}
