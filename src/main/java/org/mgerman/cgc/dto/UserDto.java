package org.mgerman.cgc.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private UUID id;
  private String email;
  private String firstName;
  private String lastName;
  private byte[] avatar;
  private String role;
}
