package com.atechproc.mapper;

import com.atechproc.dto.UserDto;
import com.atechproc.model.User;

public class UserMapper {
    public static UserDto toDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().toString());
        userDto.setFavoritesRestaurantIds(user.getFavoritesRestaurantIds());

        return userDto;
    }
}
