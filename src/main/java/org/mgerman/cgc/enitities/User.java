package org.mgerman.cgc.enitities;

import java.nio.ByteBuffer;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class User {

  @PrimaryKey
  private UUID id;

  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private ByteBuffer avatar;
  private String role;
}
