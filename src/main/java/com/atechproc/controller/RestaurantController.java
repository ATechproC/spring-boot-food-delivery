package com.atechproc.controller;

import com.atechproc.dto.FavResDto;
import com.atechproc.dto.ResDto;
import com.atechproc.response.ApiResponse;
import com.atechproc.service.res.IResService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        FavResDto res = resService.addResToFavorite(resId, jwt);
        return ResponseEntity.ok(new ApiResponse("Added to favorites", res));
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
}
