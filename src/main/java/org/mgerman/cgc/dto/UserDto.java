package org.mgerman.cgc.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = "password")
@Builder
public class UserDto {
  private UUID id;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private byte[] avatar;
  private String role;
}
