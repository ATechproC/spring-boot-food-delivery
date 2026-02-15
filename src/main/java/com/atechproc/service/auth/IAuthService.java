package com.atechproc.service.auth;

import com.atechproc.request.auth.LoginRequest;
import com.atechproc.request.auth.SignupRequest;
import com.atechproc.response.AuthResponse;

public interface IAuthService {
    AuthResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
}
