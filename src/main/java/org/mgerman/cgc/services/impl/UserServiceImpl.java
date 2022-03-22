package org.mgerman.cgc.services.impl;

import com.datastax.oss.protocol.internal.util.Bytes;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mgerman.cgc.dto.UserDto;
import org.mgerman.cgc.enitities.User;
import org.mgerman.cgc.mapper.UserMapper;
import org.mgerman.cgc.repositories.UserRepository;
import org.mgerman.cgc.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@GraphQLApi
@RequiredArgsConstructor
//todo mgerman write tests for security and graphQL?
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  @GraphQLQuery
  @PreAuthorize("hasRole('ADMIN')")
  public UserDto createUser(UserDto userDto) {
    log.info("Creating user: {}", userDto);
    User userForSave = userMapper.dtoToUser(userDto);
    userForSave.setPassword(passwordEncoder.encode(userForSave.getPassword()));
    User savedUser = userRepository.save(userForSave);
    return userMapper.userToDto(savedUser);
  }

  @Override
  @GraphQLQuery
  @PreAuthorize("hasRole('ADMIN')")
  public UserDto getUserById(UUID uuid) {
    log.info("Getting user by id {}", uuid);
    User user = userRepository.findById(uuid).orElse(null);
    return userMapper.userToDto(user);
  }

  @Override
  @GraphQLQuery
  @PreAuthorize("hasRole('ADMIN')")
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
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteUser(UUID uuid) {
    log.info("deleting user by id {}", uuid);
    userRepository.deleteById(uuid);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      log.error("User not found ");
      throw new UsernameNotFoundException("User not found");
    }

    log.info("User found: {}", email);

    List<SimpleGrantedAuthority> authorities = Stream.of(user.getRole())
        .map(SimpleGrantedAuthority::new).toList();
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
        authorities);
  }
}
