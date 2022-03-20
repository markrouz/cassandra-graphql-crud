package org.mgerman.cgc.services;

import java.util.UUID;
import org.mgerman.cgc.dto.UserDto;

public interface UserService {

  UserDto createUser(UserDto userDto);

  UserDto getUserById(UUID uuid);

  UserDto updateUser(UserDto userDto);

  void deleteUser(UUID uuid);

}
