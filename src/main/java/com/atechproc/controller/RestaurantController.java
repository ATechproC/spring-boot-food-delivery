package com.atechproc.controller;

import com.atechproc.dto.ResDto;
import com.atechproc.model.RestaurantDto;
import com.atechproc.response.ApiResponse;
import com.atechproc.service.res.IResService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.ListView;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final IResService resService;

    @RequestMapping("/add-to-favorite/{resId}")
    public ResponseEntity<ApiResponse> addToFavoriteHandler(
            @PathVariable("resId") Long resId,
            @RequestHeader("Authorization") String jwt
    ) {
        String response = resService.addResToFavorite(resId, jwt);
        return ResponseEntity.ok(new ApiResponse(response, ""));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchForResHandler(
            @RequestParam String keyword
    ) {
        List<ResDto> res = resService.searchForRestaurants(keyword);
        return ResponseEntity.ok(new ApiResponse("Success",  res));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllRestaurants() {
        List<ResDto> res = resService.getAllRestaurants();
        return ResponseEntity.ok(new ApiResponse("Success",  res));
    }

    @GetMapping("/{resId}")
    public ResponseEntity<ApiResponse> getResByIdHandler(
            @PathVariable("resId") Long resId
    ) {
        ResDto res = resService.getByRestaurantId(resId);
        return ResponseEntity.ok(new ApiResponse("Success",  res));
    }

    @GetMapping("/isFavorite/{resId}")
    public ResponseEntity<ApiResponse> isFavoriteResHandler(
            @PathVariable("resId") Long resId,
            @RequestHeader("Authorization") String jwt
    ) {
        Boolean isFavorite = resService.isUserFavoriteRestaurant(jwt, resId);
        return ResponseEntity.ok(new ApiResponse("Success", isFavorite));
    }

    @GetMapping("/user/favorite")
    public ResponseEntity<ApiResponse> getUserFavoriteRestaurants(
            @RequestHeader("Authorization") String jwt
    ) {
        List<ResDto> res = resService.getUserFavoriteRestaurant(jwt);
        return ResponseEntity.ok(new ApiResponse("Success", res));
    }


}
