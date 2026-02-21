package com.atechproc.controller;

import com.atechproc.dto.ResDto;
import com.atechproc.mapper.ResMapper;
import com.atechproc.model.Restaurant;
import com.atechproc.request.res.CreateRestaurantRequest;
import com.atechproc.request.res.UpdateRestaurantRequest;
import com.atechproc.response.ApiResponse;
import com.atechproc.service.res.IResService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/admin/restaurant")
@RequiredArgsConstructor
public class AdminRestaurantController {

    private final IResService resService;

    @PostMapping("/create-restaurant")
    public ResponseEntity<ApiResponse> createRestaurantHandler(
            @Valid
            @RequestBody
            CreateRestaurantRequest request,
            @RequestHeader("Authorization") String jwt
    ) {
        ResDto res = resService.createRestaurant(request, jwt);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Restaurant created successfully", res));
    }

    @PutMapping("/update-restaurant/{resId}")
    public ResponseEntity<ApiResponse> updateRestaurant(
            @Valid
            @RequestBody
            UpdateRestaurantRequest request,
            @PathVariable("resId") Long id,
            @RequestParam("userId") Long userId)
            throws BadRequestException {
        ResDto res = resService.updateRestaurant(id, request, userId);
        return ResponseEntity.ok(new ApiResponse("Success", res));
    }

    @DeleteMapping("/delete-restaurant/{resId}")
    public ResponseEntity<ApiResponse> deleteResByIdHandler(
            @PathVariable("resId") Long resId,
            @RequestParam("userId") Long userId
    ) throws BadRequestException {
        resService.deleteRestaurant(resId, userId);
        return ResponseEntity.ok(new ApiResponse("Res deleted successfully", null));
    }

    @GetMapping("/by-user")
    public ResponseEntity<ApiResponse> getResByUserIdHandler(
            @RequestHeader("Authorization") String jwt
    ) throws BadRequestException {
        Restaurant res = resService.getResByUser(jwt);
        ResDto resDto = ResMapper.toDto(res);
        return ResponseEntity.ok(new ApiResponse("Success", resDto));
    }

    @PutMapping("/update-status")
    public ResponseEntity<ApiResponse>  updateResStatusHandler(
            @RequestHeader("Authorization") String jwt,
            @RequestParam Long resId
    ) throws BadRequestException {
        ResDto res = resService.updateResStatus(jwt, resId);
        return ResponseEntity.ok(new ApiResponse("Restaurant status updated successfully", res));
    }

}
