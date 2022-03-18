package org.mgerman.cgc.repositories;

import java.util.UUID;
import org.mgerman.cgc.enitities.User;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserRepository extends CassandraRepository<User, UUID> {

}
