package com.atechproc.dto;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String role;
    private List<Long> favoritesRestaurantIds;
}
