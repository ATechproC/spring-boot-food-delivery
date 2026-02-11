package com.atechproc.service.auth;

import com.atechproc.dto.UserDto;
import com.atechproc.request.LoginRequest;
import com.atechproc.request.SignupRequest;
import com.atechproc.response.AuthResponse;

public interface IAuthService {
    AuthResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
}
