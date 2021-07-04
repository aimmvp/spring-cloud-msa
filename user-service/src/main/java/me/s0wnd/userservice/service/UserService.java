package me.s0wnd.userservice.service;

import me.s0wnd.userservice.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
