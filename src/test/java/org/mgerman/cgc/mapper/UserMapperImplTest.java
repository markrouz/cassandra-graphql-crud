package org.mgerman.cgc.mapper;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mgerman.cgc.TestUserCreator.createUser;
import static org.mgerman.cgc.TestUserCreator.createUserDto;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mgerman.cgc.dto.UserDto;
import org.mgerman.cgc.enitities.User;
import org.mgerman.cgc.mapper.impl.UserMapperImpl;

public class UserMapperImplTest {

  private UserMapperImpl userMapper = new UserMapperImpl();

  @Test
  public void userToDtoNullTest() {
    assertNull(userMapper.userToDto(null));
  }

  @Test
  public void dtoToUserNullTest() {
    assertNull(userMapper.dtoToUser(null));
  }

  @Test
  public void userToDtoTest() {
    User user = createUser(UUID.randomUUID());

    UserDto userDto = userMapper.userToDto(user);

    assertNotNull(userDto);
    assertEquals(user.getId(), userDto.getId());
    assertEquals(user.getEmail(), userDto.getEmail());
    assertEquals(user.getFirstName(), userDto.getFirstName());
    assertEquals(user.getLastName(), userDto.getLastName());
    assertEquals(user.getAvatar().array(), userDto.getAvatar());
    assertEquals(user.getRole(), userDto.getRole());
  }

  @Test
  public void dtoToUserTest() {
    UserDto userDto = createUserDto(UUID.randomUUID());

    User user = userMapper.dtoToUser(userDto);
    assertNotNull(userDto);
    assertEquals(user.getId(), userDto.getId());
    assertEquals(user.getEmail(), userDto.getEmail());
    assertEquals(user.getFirstName(), userDto.getFirstName());
    assertArrayEquals(user.getAvatar().array(), userDto.getAvatar());
    assertEquals(user.getRole(), userDto.getRole());
  }

}
