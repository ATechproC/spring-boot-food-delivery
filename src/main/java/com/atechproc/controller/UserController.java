package com.atechproc.controller;

import com.atechproc.dto.UserDto;
import com.atechproc.mapper.UserMapper;
import com.atechproc.model.User;
import com.atechproc.response.ApiResponse;
import com.atechproc.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) {
        User user = userService.getUserProfile(jwt);
        UserDto userDto = UserMapper.toDto(user);
        return ResponseEntity.ok(new ApiResponse("Success", userDto));
    }
}
