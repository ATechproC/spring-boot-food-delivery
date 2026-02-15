package com.atechproc.service.user;

import com.atechproc.dto.UserDto;
import com.atechproc.exception.ResourceNotFoundException;
import com.atechproc.mapper.UserMapper;
import com.atechproc.model.User;
import com.atechproc.repository.UserRepository;
import com.atechproc.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements  IUserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User getUserProfile(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        return getUserByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new ResourceNotFoundException("User not found with email " + email);
        }
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
