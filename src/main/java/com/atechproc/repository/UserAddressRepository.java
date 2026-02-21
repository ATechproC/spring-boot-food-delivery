package com.atechproc.repository;

import com.atechproc.model.User;
import com.atechproc.model.UserAddress;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    Optional<UserAddress> findByStreetAddressAndCountryAndPostalCodeAndStateProvinceAndCostumer(
            String streetAddress,
            String country,
            String postalCode,
            String stateProvince,
            User user
    );
}
