package me.jarvica.santander.thirdexercise.service.consumer;

import me.jarvica.santander.thirdexercise.BaseTest;
import me.jarvica.santander.thirdexercise.model.Notification;
import me.jarvica.santander.thirdexercise.repository.NotificationRepository;
import me.jarvica.santander.thirdexercise.service.producer.NotificationProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
class NotificationConsumerTest extends BaseTest {

  @Autowired
  @Qualifier("kafka")
  private NotificationProducer kafkaProducer;

  @Autowired
  @Qualifier("rabbit")
  private NotificationProducer rabbitProducer;

  @Autowired
  private NotificationRepository repository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testProcess() {
    final var notification = Notification.builder()
        .message("test")
        .recipient("test")
        .build();
    this.kafkaProducer.send(notification);
    this.rabbitProducer.send(notification);
    assertSame(this.repository.findNotificationsByRecipient("test").size(), 2);
  }
}
