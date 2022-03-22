package org.mgerman.cgc;

import com.datastax.oss.protocol.internal.util.Bytes;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.mgerman.cgc.enitities.User;
import org.mgerman.cgc.repositories.UserRepository;
import org.mgerman.cgc.utils.JpgUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Slf4j
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner insertUsersToCassandraRunner(UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    return args -> {
      userRepository.deleteAll();

      UUID johnUUID = UUID.randomUUID();
      var john = new User(johnUUID, "john@gmail.com", passwordEncoder.encode("john"), "John", "Doe",
          Bytes.fromHexString(Bytes.toHexString(
              JpgUtils.convertImageToByteBuffer("src/main/resources/avatars/john.jpg"))), "USER");
      UUID janeUUID = UUID.randomUUID();
      var jane = new User(janeUUID, "jane@gmail.com", passwordEncoder.encode("jane"), "Jane", "Doe",
          Bytes.fromHexString(Bytes.toHexString(
              JpgUtils.convertImageToByteBuffer("src/main/resources/avatars/jane.jpg"))), "ADMIN");
      log.info("creating test user with id {}", johnUUID);
      log.info("creating test user with id {}", janeUUID);
      userRepository.save(john);
      userRepository.save(jane);
    };
  }

}
