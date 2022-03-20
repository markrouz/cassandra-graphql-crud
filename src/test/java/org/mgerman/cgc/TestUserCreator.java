package org.mgerman.cgc;

import com.datastax.oss.protocol.internal.util.Bytes;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.mgerman.cgc.dto.UserDto;
import org.mgerman.cgc.enitities.User;

@UtilityClass
public class TestUserCreator {

  public static UserDto createUserDto(UUID uuid) {
    return UserDto.builder()
        .id(uuid)
        .email("email")
        .firstName("firstName")
        .lastName("lastName")
        .avatar(Bytes.fromHexString("0x11").array())
        .role("ADMIN")
        .build();
  }

  public static User createUser(UUID uuid) {
    return User.builder()
        .id(uuid)
        .email("email")
        .password("password")
        .firstName("firstName")
        .lastName("lastName")
        .avatar(Bytes.fromHexString("0x11"))
        .role("ADMIN")
        .build();
  }

}
