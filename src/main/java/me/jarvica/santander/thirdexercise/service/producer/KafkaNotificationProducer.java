package me.jarvica.santander.thirdexercise.service.producer;

import lombok.extern.slf4j.Slf4j;
import me.jarvica.santander.thirdexercise.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("kafka")
public final class KafkaNotificationProducer implements NotificationProducer {

  private final KafkaTemplate<String, Notification> kafkaTemplate;

  @Autowired
  KafkaNotificationProducer(KafkaTemplate<String, Notification> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void send(final Notification notification) {
    this.kafkaTemplate.send(Notification.TOPIC, notification);
    log.info("Notification sent with kafka: {}", notification);
  }
}
