package org.mgerman.cgc;

import com.datastax.oss.protocol.internal.util.Bytes;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.mgerman.cgc.enitities.User;
import org.mgerman.cgc.repositories.UserRepository;
import org.mgerman.cgc.utils.ImageUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner insertUsersToCassandraRunner(UserRepository userRepository) {
    return args -> {
      userRepository.deleteAll();

      var john = new User(UUID.randomUUID(), "john@gmail.com", "john", "John", "Doe",
          Bytes.fromHexString(Bytes.toHexString(
              ImageUtils.convertImageToByteBuffer("src/main/resources/avatars/john.jpg"))), "USER");
      var jane = new User(UUID.randomUUID(), "jane@gmail.com", "jane", "Jane", "Doe",
          Bytes.fromHexString(Bytes.toHexString(
              ImageUtils.convertImageToByteBuffer("src/main/resources/avatars/jane.jpg"))), "ADMIN");
      userRepository.save(john);
      userRepository.save(jane);
    };
  }

}
