package com.atechproc.service.auth;

import com.atechproc.exception.AlreadyExistsException;
import com.atechproc.model.User;
import com.atechproc.repository.UserRepository;
import com.atechproc.request.LoginRequest;
import com.atechproc.request.SignupRequest;
import com.atechproc.response.AuthResponse;
import com.atechproc.security.jwt.JwtProvider;
import com.atechproc.security.user.FoodDeliveryUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;
    private final FoodDeliveryUserDetailsService userDetailsService;

    @Override
    public AuthResponse signup(SignupRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if(user != null) {
            throw new AlreadyExistsException("Account already exists!");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(request.getRole());

        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        return new AuthResponse(
                "Account created successfully",
                jwt,
                newUser.getRole().toString()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticate(request.getEmail(), request.getPassword());
        User user = userRepository.findByEmail(request.getEmail());
        String jwt = jwtProvider.generateToken(authentication);
        return new AuthResponse(
                "Sign In successfully",
                jwt,
                user.getRole().toString()
        );
    }

    private Authentication authenticate(String email, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if(userDetails == null ||
                passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
