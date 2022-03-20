package org.mgerman.cgc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mgerman.cgc.TestUserCreator.createUserDto;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mgerman.cgc.dto.UserDto;
import org.mgerman.cgc.enitities.User;
import org.mgerman.cgc.repositories.UserRepository;
import org.mgerman.cgc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class UserServiceIntegrationTest extends AbstractCassandraIntegrationTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void createUserTest() {
    UUID randomUUID = UUID.randomUUID();
    UserDto testUserDto = createUserDto(randomUUID);

    UserDto createdUserDto = userService.createUser(testUserDto);

    assertNotNull(createdUserDto);
    assertEquals(createdUserDto, testUserDto);
  }

  @Test
  public void getUserByIdTest() {
    UUID userUUID = UUID.randomUUID();
    UserDto testUserDto = createUserDto(userUUID);
    UserDto createdUserDto = userService.createUser(testUserDto);

    UserDto foundUserDto = userService.getUserById(userUUID);

    assertNotNull(foundUserDto);
    assertEquals(foundUserDto, createdUserDto);
  }

  @Test
  public void updateUserTest() {
    UUID userUUID = UUID.randomUUID();
    UserDto testUserDto = createUserDto(userUUID);
    UserDto beforeUpdateUserDto = userService.createUser(testUserDto);
    User beforeUpdateUser = userRepository.findById(userUUID).orElseThrow();

    UserDto userDto = userService.updateUser(testUserDto);

    assertNotNull(userDto);
    assertEquals(userDto, beforeUpdateUserDto);
    //checking that password doesn't changed
    User afterUpdateUser = userRepository.findById(userUUID).orElseThrow();
    assertEquals(beforeUpdateUser, afterUpdateUser);
  }

  @Test
  public void deleteUserTest() {
    UUID userUUID = UUID.randomUUID();
    UserDto testUserDto = createUserDto(userUUID);
    userService.createUser(testUserDto);

    userService.deleteUser(userUUID);

    assertFalse(userRepository.existsById(userUUID));
    assertNull(userService.getUserById(userUUID));
  }


}
