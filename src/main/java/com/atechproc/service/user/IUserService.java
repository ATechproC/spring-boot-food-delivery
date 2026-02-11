package com.atechproc.service.user;

import com.atechproc.dto.UserDto;
import com.atechproc.model.User;

public interface IUserService {
    UserDto getUserProfile(String jwt);
    User getUserByEmail(String email);
}
