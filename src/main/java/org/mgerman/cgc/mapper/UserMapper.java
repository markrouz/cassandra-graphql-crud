package org.mgerman.cgc.mapper;

import org.mgerman.cgc.dto.UserDto;
import org.mgerman.cgc.enitities.User;

public interface UserMapper {

  UserDto userToDto(User user);

  User dtoToUser(UserDto userDto);
}