package org.mgerman.cgc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mgerman.cgc.enitities.User;
import org.mgerman.cgc.repositories.UserRepository;
import org.mgerman.cgc.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class CassandraIntegrationCrudTest extends AbstractCassandraIntegrationTest {

  @Autowired
  protected UserRepository userRepository;

  @Test
  void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
    assertThat(cassandra.isRunning()).isTrue();
  }

  @Test
  void givenValidUser_whenSavingIt_thenUserIsSaved() throws IOException {
    UUID johnUUID = UUID.randomUUID();
    var john = new User(johnUUID, "john@gmail.com", "john", "John", "Doe",
        ImageUtils.convertImageToByteBuffer("src/main/resources/avatars/john.jpg"), "USER");

    userRepository.save(john);

    List<User> users = userRepository.findAllById(List.of(johnUUID));
    assertThat(users).hasSize(1);
    assertThat(users.get(0)).isEqualTo(john);
  }

  @Test
  void givenExistingUser_whenUpdatingIt_thenUserIsUpdated() throws IOException {
    UUID johnUUID = UUID.randomUUID();
    var john = new User(johnUUID, "john@gmail.com", "john", "John", "Doe",
        ImageUtils.convertImageToByteBuffer("src/main/resources/avatars/john.jpg"), "USER");
    User existingUser = userRepository.save(john);

    existingUser.setEmail("john1@gmail.com");
    userRepository.save(existingUser);

    List<User> users = userRepository.findAllById(List.of(johnUUID));
    assertThat(users).hasSize(1);
    assertThat(users.get(0).getEmail()).isEqualTo("john1@gmail.com");
  }

  @Test
  void givenExistingUser_whenDeletingIt_thenUserIsDeleted() throws IOException {
    UUID johnUUID = UUID.randomUUID();
    var john = new User(johnUUID, "john@gmail.com", "john", "John", "Doe",
        ImageUtils.convertImageToByteBuffer("src/main/resources/avatars/john.jpg"), "USER");
    User existingUser = userRepository.save(john);

    userRepository.delete(existingUser);

    List<User> users = userRepository.findAllById(List.of(johnUUID));
    assertThat(users).isEmpty();
  }
}
