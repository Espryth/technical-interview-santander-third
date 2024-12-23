package me.jarvica.santander.thirdexercise.service;

import me.jarvica.santander.thirdexercise.repository.NotificationRepository;
import me.jarvica.santander.thirdexercise.service.producer.KafkaNotificationProducer;
import me.jarvica.santander.thirdexercise.service.producer.NotificationProducerType;
import me.jarvica.santander.thirdexercise.service.producer.RabbitNotificationProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

  @Mock
  private NotificationRepository repository;

  @Mock
  private KafkaNotificationProducer kafkaProducer;

  @Mock
  private RabbitNotificationProducer rabbitProducer;

  @InjectMocks
  private NotificationService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSend() {
    doNothing().when(this.kafkaProducer).send(any());
    final var result = this.service.send(NotificationProducerType.KAFKA, null).join();
    assertSame(result.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void testSendFail() {
    doThrow(new RuntimeException()).when(this.rabbitProducer).send(any());
    final var result = this.service.send(NotificationProducerType.RABBIT, null).join();
    assertSame(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
