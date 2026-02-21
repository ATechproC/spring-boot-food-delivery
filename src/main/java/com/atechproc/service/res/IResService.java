package com.atechproc.service.res;

import com.atechproc.dto.FavResDto;
import com.atechproc.dto.ResDto;
import com.atechproc.model.Restaurant;
import com.atechproc.model.User;
import com.atechproc.request.res.CreateRestaurantRequest;
import com.atechproc.request.res.UpdateRestaurantRequest;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IResService {
    ResDto createRestaurant(CreateRestaurantRequest request, String jwt);
    ResDto updateRestaurant(Long id, UpdateRestaurantRequest request, Long userId) throws BadRequestException;
    void deleteRestaurant(Long id, Long userId) throws BadRequestException;
    Restaurant getResByUserId(Long userId);
    Restaurant getResByUser(String jwt);
    ResDto updateResStatus(String jwt, Long userId) throws BadRequestException;
    List<ResDto> getAllRestaurants();
    List<ResDto> searchForRestaurants(String keyword);
    ResDto getByRestaurantId(Long resId);
    FavResDto addResToFavorite(Long resId, String jwt);
}