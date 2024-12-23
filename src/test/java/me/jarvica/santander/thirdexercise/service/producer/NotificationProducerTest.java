package me.jarvica.santander.thirdexercise.service.producer;

import me.jarvica.santander.thirdexercise.BaseTest;
import me.jarvica.santander.thirdexercise.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Testcontainers
class NotificationProducerTest extends BaseTest {

  @Autowired
  @Qualifier("kafka")
  private NotificationProducer kafkaProducer;

  @Autowired
  @Qualifier("rabbit")
  private NotificationProducer rabbitProducer;

  @Test
  void testSend() {
    final var notification = Notification.builder()
        .message("test")
        .recipient("test")
        .build();
    assertDoesNotThrow(() -> this.kafkaProducer.send(notification));
    assertDoesNotThrow(() -> this.rabbitProducer.send(notification));
  }
}
