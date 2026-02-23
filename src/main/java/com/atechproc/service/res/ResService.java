package com.atechproc.service.res;

import com.atechproc.dto.ResDto;
import com.atechproc.exception.AlreadyExistsException;
import com.atechproc.exception.ResourceNotFoundException;
import com.atechproc.mapper.ResMapper;
import com.atechproc.model.*;
import com.atechproc.repository.ResRepository;
import com.atechproc.repository.RestaurantAddressRepository;
import com.atechproc.repository.UserRepository;
import com.atechproc.request.res.CreateRestaurantRequest;
import com.atechproc.request.res.UpdateRestaurantRequest;
import com.atechproc.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResService implements IResService {

    private final ResRepository resRepository;
    private final IUserService userService;
    private final RestaurantAddressRepository restaurantAddressRepository;
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_OWNER')")
    public ResDto createRestaurant(CreateRestaurantRequest request, String jwt) {

        User owner = userService.getUserProfile(jwt);

        Restaurant res = resRepository.findByName(request.getName());

        if(res != null) {
            throw new AlreadyExistsException("Restaurant name already exists!");
        }

        Restaurant restaurant = new Restaurant();

        RestaurantAddress address = new RestaurantAddress();
        address.setStreetAddress(request.getAddress().getStreetAddress());
        address.setStateProvince(request.getAddress().getStateProvince());
        address.setCountry(request.getAddress().getCountry());
        address.setCity(request.getAddress().getCity());

        ContactInfo info = new ContactInfo();
        info.setEmail(request.getContactInformation().getEmail());
        info.setInstagram(request.getContactInformation().getInstagram());
        info.setMobile(request.getContactInformation().getMobile());
        info.setTwitter(request.getContactInformation().getTwitter());

        RestaurantAddress savedAddress = restaurantAddressRepository.save(address);

        restaurant.setAddress(savedAddress);

        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setTitle(request.getTitle());
        restaurant.setContactInformation(info);
        restaurant.setCuisineType(request.getCuisineType());
        restaurant.setNumRating(request.getNumRating());
        restaurant.setOpeningHours(LocalTime.parse(request.getOpeningHours()));
        restaurant.setImages(request.getImages());
        restaurant.setOwner(owner);
        restaurant.setRegistrationDate(LocalDateTime.parse(request.getRegistrationDate()));

        Restaurant savedRes = resRepository.save(restaurant);

        return ResMapper.toDto(savedRes);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_OWNER')")
    public ResDto updateRestaurant(Long id, UpdateRestaurantRequest request, Long userId)
            throws BadRequestException {

        Restaurant existingRes = findRestaurantById(id);

        if(!existingRes.getOwner().getId().equals(userId)) {
            throw new BadRequestException("Your not allowed to modify this restaurant info");
        }

        if (request.getName() != null) {
            existingRes.setName(request.getName());
        }

        if (request.getDescription() != null) {
            existingRes.setDescription(request.getDescription());
        }

        if (request.getCuisineType() != null) {
            existingRes.setCuisineType(request.getCuisineType());
        }

        if (request.getContactInformation() != null) {
            existingRes.setContactInformation(request.getContactInformation());
        }

        if (request.getOpeningHours() != null) {
            existingRes.setOpeningHours(request.getOpeningHours());
        }

        if (request.getNumRating() != null) {
            existingRes.setNumRating(request.getNumRating());
        }

        if (request.getImages() != null) {
            existingRes.setImages(request.getImages());
        }

        if (request.getRegistrationDate() != null) {
            existingRes.setRegistrationDate(request.getRegistrationDate());
        }

        if(request.getAddress() != null) {
            if(request.getAddress().getStreetAddress() != null) {
                existingRes.getAddress().setStreetAddress(request.getAddress().getStreetAddress() );
            }

            if(request.getAddress().getCountry() != null) {
                existingRes.getAddress().setCountry(request.getAddress().getCountry());
            }
        }

        if(request.getContactInformation() != null) {
            if(request.getContactInformation().getEmail() != null) {
                existingRes.getContactInformation().setEmail(request.getContactInformation().getEmail());
            }

            if(request.getContactInformation().getMobile() != null) {
                existingRes.getContactInformation().setMobile(request.getContactInformation().getMobile());
            }

            if(request.getContactInformation().getInstagram() != null) {
                existingRes.getContactInformation().setInstagram(request.getContactInformation().getInstagram());
            }

            if(request.getContactInformation().getTwitter() != null) {
                existingRes.getContactInformation().setTwitter(request.getContactInformation().getTwitter());
            }
        }

        Restaurant savedRes = resRepository.save(existingRes);

        return ResMapper.toDto(savedRes);
    }

    private Restaurant findRestaurantById(Long id) {
        return resRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with Id " + id));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_OWNER')")
    public void deleteRestaurant(Long id, Long userId)
            throws BadRequestException {
        Restaurant res = findRestaurantById(id);

        if(!res.getOwner().getId().equals(userId)) {
            throw new BadRequestException("You are not allowed to delete this restaurant");
        }

        resRepository.deleteById(id);
    }

    @Override
    public List<ResDto> getAllRestaurants() {
        List<Restaurant> restaurants = resRepository.findAll();
        return ResMapper.toDTOs(restaurants);
    }

    @Override
    public List<ResDto> searchForRestaurants(String keyword) {
        List<Restaurant> restaurants = resRepository.searchResQuery(keyword);
        return ResMapper.toDTOs(restaurants);
    }

    @Override
    public ResDto getByRestaurantId(Long resId) {
        Restaurant res = findRestaurantById(resId);
        return ResMapper.toDto(res);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_OWNER')")
    public Restaurant getResByUserId(Long userId) {
        return resRepository.findByOwner_id(userId);
    }

    @Override
    public String addResToFavorite(Long resId, String jwt){

        Restaurant res = findRestaurantById(resId);
        User user = userService.getUserProfile(jwt);

        if(user.getFavoritesRestaurantIds().contains(res.getId())) {
            user.getFavoritesRestaurantIds().remove(res.getId());
            userRepository.save(user);
            return "Removed from favorites";
        }else {
            user.getFavoritesRestaurantIds().add(res.getId());
            userRepository.save(user);
            return "Added to favorites";
        }
    }

    @Override
    public Boolean isUserFavoriteRestaurant(String jwt, Long resId) {
        User user = userService.getUserProfile(jwt);
        Restaurant res = findRestaurantById(resId);
        return user.getFavoritesRestaurantIds().contains(resId);
    }

    @Override
    public List<ResDto> getUserFavoriteRestaurant(String jwt) {
        User user = userService.getUserProfile(jwt);

        List<Long> ids = user.getFavoritesRestaurantIds();

        List<Restaurant> restaurants = new ArrayList<>();

        for(Long id : ids) {
            Restaurant res = findRestaurantById(id);
            restaurants.add(res);
        }

        return ResMapper.toDTOs(restaurants);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_OWNER')")
    public ResDto updateResStatus(String jwt, Long resId)
            throws BadRequestException {
        User owner = userService.getUserProfile(jwt);
        Restaurant res = resRepository.findByOwner_id(owner.getId());

        if(!res.getId().equals(resId)) {
            throw new BadRequestException("You're not allowed to update this restaurant status");
        }

        res.setOpen(!res.getOpen());

        Restaurant savedRes = resRepository.save(res);

        return ResMapper.toDto(res);
    }

    @Override
    public Restaurant getResByUser(String jwt) {
        User owner = userService.getUserProfile(jwt);
        return resRepository.findByOwner(owner);
    }
}
