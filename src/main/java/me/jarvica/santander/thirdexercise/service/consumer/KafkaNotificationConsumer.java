package me.jarvica.santander.thirdexercise.service.consumer;

import lombok.extern.slf4j.Slf4j;
import me.jarvica.santander.thirdexercise.model.Notification;
import me.jarvica.santander.thirdexercise.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public final class KafkaNotificationConsumer implements NotificationConsumer<Notification> {

  private final NotificationRepository repository;

  @Autowired
  KafkaNotificationConsumer(final NotificationRepository repository) {
    this.repository = repository;
  }

  @KafkaListener(topics = Notification.TOPIC, groupId = Notification.TOPIC + "_group")
  @Override
  public void process(final Notification notification) {
    log.info("Processing notification with kafka: {}", notification);
    notification.setPublishedAt(new Date());
    this.repository.save(notification);
  }
}
