package me.s0wnd.userservice.service;

import me.s0wnd.userservice.dto.UserDto;
import me.s0wnd.userservice.jpa.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
