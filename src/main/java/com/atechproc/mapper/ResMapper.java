package com.atechproc.mapper;

import com.atechproc.dto.FavResDto;
import com.atechproc.dto.ResDto;
import com.atechproc.dto.RestaurantAddressDto;
import com.atechproc.model.FavoriteRestaurant;
import com.atechproc.model.Restaurant;

import java.util.List;

public class ResMapper {
    public static ResDto toDto(Restaurant res) {

        ResDto resDto = new ResDto();

        RestaurantAddressDto restaurantAddressDto = new RestaurantAddressDto();
        restaurantAddressDto.setId(res.getAddress().getId());
        restaurantAddressDto.setCity(res.getAddress().getCity());
        restaurantAddressDto.setCountry(res.getAddress().getCountry());
        restaurantAddressDto.setStreetAddress(res.getAddress().getStreetAddress());
        restaurantAddressDto.setStateProvince(res.getAddress().getStateProvince());

        resDto.setId(res.getId());
        resDto.setName(res.getName());
        resDto.setDescription(res.getDescription());
        resDto.setAddress(restaurantAddressDto);
        resDto.setContactInformation(res.getContactInformation());
        resDto.setCuisineType(res.getCuisineType());
        resDto.setNumRating(res.getNumRating());
        resDto.setOpeningHours(res.getOpeningHours());
        resDto.setImages(res.getImages());
        resDto.setOpen(res.getOpen());
        resDto.setRegistrationDate(res.getRegistrationDate());

        return resDto;
    }

    public static List<ResDto> toDTOs(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(ResMapper::toDto).toList();
    }

    public static FavResDto toFavRes(FavoriteRestaurant res) {
        FavResDto resDto = new FavResDto();
        resDto.setId(res.getId());
        resDto.setTitle(res.getTitle());
        resDto.setImages(res.getImages());
        resDto.setDescription(res.getDescription());

        return resDto;
    }

    public static List<FavResDto> toFavResS(List<FavoriteRestaurant> restaurants) {
        return restaurants.stream().map(ResMapper::toFavRes).toList();
    }
}
