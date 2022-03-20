package org.mgerman.cgc.services.impl;

import com.datastax.oss.protocol.internal.util.Bytes;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mgerman.cgc.dto.UserDto;
import org.mgerman.cgc.enitities.User;
import org.mgerman.cgc.mapper.UserMapper;
import org.mgerman.cgc.repositories.UserRepository;
import org.mgerman.cgc.services.UserService;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@GraphQLApi
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  @GraphQLQuery
  public UserDto createUser(UserDto userDto) {
    log.info("Creating user: {}", userDto);
    User userForSave = userMapper.dtoToUser(userDto);
    User savedUser = userRepository.save(userForSave);
    return userMapper.userToDto(savedUser);
  }

  @Override
  @GraphQLQuery
  public UserDto getUserById(UUID uuid) {
    log.info("Getting user by id {}", uuid);
    User user = userRepository.findById(uuid).orElse(null);
    return userMapper.userToDto(user);
  }

  @Override
  @GraphQLQuery
  public UserDto updateUser(UserDto userDto) {
    log.info("Updating user by dto {}", userDto);
    User user = userRepository.findById(userDto.getId()).orElseThrow(
        () -> new IllegalArgumentException(
            String.format("user with id %s doesn't exists", userDto.getId())));

    user.setEmail(userDto.getEmail());
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setAvatar(Bytes.fromHexString(Bytes.toHexString(userDto.getAvatar())));
    user.setRole(userDto.getRole());

    User savedUser = userRepository.save(user);

    return userMapper.userToDto(savedUser);
  }

  @Override
  @GraphQLQuery
  public void deleteUser(UUID uuid) {
    log.info("deleting user by id {}", uuid);
    userRepository.deleteById(uuid);
  }
}
