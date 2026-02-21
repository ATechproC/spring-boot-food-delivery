package com.atechproc.repository;

import com.atechproc.model.RestaurantAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantAddressRepository extends JpaRepository<RestaurantAddress, Long> {
}
