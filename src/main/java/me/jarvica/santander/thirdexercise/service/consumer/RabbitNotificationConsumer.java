package me.jarvica.santander.thirdexercise.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.jarvica.santander.thirdexercise.model.Notification;
import me.jarvica.santander.thirdexercise.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public final class RabbitNotificationConsumer implements NotificationConsumer<byte[]> {

  @Autowired
  private NotificationRepository repository;

  @Autowired
  private ObjectMapper objectMapper;

  @RabbitListener(queues = Notification.TOPIC)
  @Override
  public void process(final byte[] bytes) {
    try {
      final var notification = this.objectMapper.readValue(bytes, Notification.class);
      log.info("Processing notification with rabbit: {}", notification);
      notification.setPublishedAt(new Date());
      this.repository.save(notification);
    } catch (final Exception e) {
      log.error("Error processing notification with rabbit", e);
    }
  }
}
