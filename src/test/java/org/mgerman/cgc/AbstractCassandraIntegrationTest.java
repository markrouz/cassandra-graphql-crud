package org.mgerman.cgc;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;

public class AbstractCassandraIntegrationTest {
  private static final String KEYSPACE_NAME = "test";

  @Container
  public static final CassandraContainer cassandra
      = (CassandraContainer) new CassandraContainer("cassandra:latest").withExposedPorts(9042);

  @BeforeAll
  static void setupCassandraConnectionProperties() {
    System.setProperty("spring.data.cassandra.keyspace-name", KEYSPACE_NAME);
    System.setProperty("spring.data.cassandra.contact-points", cassandra.getContainerIpAddress());
    System.setProperty("spring.data.cassandra.port", String.valueOf(cassandra.getMappedPort(9042)));

    createKeyspace(cassandra.getCluster());
  }

  static void createKeyspace(Cluster cluster) {
    try (Session session = cluster.connect()) {
      session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH replication = \n" +
          "{'class':'SimpleStrategy','replication_factor':'1'};");
    }
  }
}
