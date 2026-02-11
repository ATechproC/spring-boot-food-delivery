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
    public UserDto getUserProfile(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        User user = getUserByEmail(email);

        return UserMapper.toDto(user);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new ResourceNotFoundException("User not found with email " + email);
        }
        return user;
    }
}
