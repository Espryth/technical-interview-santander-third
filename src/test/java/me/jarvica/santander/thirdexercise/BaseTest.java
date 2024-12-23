package me.jarvica.santander.thirdexercise;

import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest extends DatabaseBaseTest{


  @Container
  protected static final RabbitMQContainer RABBIT_CONTAINER = new RabbitMQContainer("rabbitmq:3-management");

  @Container
  protected static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer("apache/kafka-native:3.8.0");

  static {
    RABBIT_CONTAINER.start();
    KAFKA_CONTAINER.start();
  }

  @DynamicPropertySource
  static void setProperties(final DynamicPropertyRegistry registry) {
    registry.add("spring.rabbitmq.host", RABBIT_CONTAINER::getHost);
    registry.add("spring.rabbitmq.port", RABBIT_CONTAINER::getAmqpPort);
    registry.add("spring.rabbitmq.username", RABBIT_CONTAINER::getAdminUsername);
    registry.add("spring.rabbitmq.password", RABBIT_CONTAINER::getAdminPassword);
    registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
  }

}
