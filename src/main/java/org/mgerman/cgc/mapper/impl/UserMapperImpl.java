package org.mgerman.cgc.mapper.impl;

import com.datastax.oss.protocol.internal.util.Bytes;
import org.mgerman.cgc.dto.UserDto;
import org.mgerman.cgc.enitities.User;
import org.mgerman.cgc.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public UserDto userToDto(User user) {
    if (user == null) {
      return null;
    }

    return UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .avatar(user.getAvatar().array()).role(user.getRole())
        .build();
  }

  @Override
  public User dtoToUser(UserDto userDto) {
    if (userDto == null) {
      return null;
    }

    return User.builder()
        .id(userDto.getId())
        .email(userDto.getEmail())
        .password(userDto.getPassword())
        .firstName(userDto.getFirstName())
        .lastName(userDto.getLastName())
        .avatar(Bytes.fromHexString(Bytes.toHexString(userDto.getAvatar())))
        .role(userDto.getRole())
        .build();
  }
}
