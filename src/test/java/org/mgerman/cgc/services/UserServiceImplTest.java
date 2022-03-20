package org.mgerman.cgc.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mgerman.cgc.TestUserCreator.createUser;
import static org.mgerman.cgc.TestUserCreator.createUserDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mgerman.cgc.dto.UserDto;
import org.mgerman.cgc.enitities.User;
import org.mgerman.cgc.mapper.UserMapper;
import org.mgerman.cgc.repositories.UserRepository;
import org.mgerman.cgc.services.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  public void createUserTest() {
    UUID randomUUID = UUID.randomUUID();
    UserDto testUserDto = createUserDto(randomUUID);
    User testUser = createUser(randomUUID);
    when(userRepository.save(any())).thenReturn(testUser);
    when(userMapper.userToDto(any())).thenReturn(testUserDto);

    UserDto createdUserDto = userService.createUser(testUserDto);

    assertNotNull(createdUserDto);
    assertEquals(createdUserDto, testUserDto);
  }

  @Test
  public void getUserByIdTest() {
    UUID userUUID = UUID.randomUUID();
    User testUser = createUser(userUUID);
    UserDto testUserDto = createUserDto(userUUID);
    when(userRepository.findById(any())).thenReturn(Optional.of(testUser));
    when(userMapper.userToDto(any())).thenReturn(testUserDto);

    UserDto foundUserDto = userService.getUserById(userUUID);
    assertNotNull(foundUserDto);
    assertEquals(foundUserDto, testUserDto);
  }

  @Test
  public void updateUserTest() {
    UUID userUUID = UUID.randomUUID();
    User testUser = createUser(userUUID);
    UserDto testUserDto = createUserDto(userUUID);
    when(userRepository.findById(any())).thenReturn(Optional.of(testUser));
    when(userMapper.userToDto(any())).thenReturn(testUserDto);

    UserDto userDto = userService.updateUser(testUserDto);
    assertNotNull(userDto);
    assertEquals(userDto, testUserDto);
  }

}
