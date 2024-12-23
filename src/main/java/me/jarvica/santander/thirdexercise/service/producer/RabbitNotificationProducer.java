package me.jarvica.santander.thirdexercise.service.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.jarvica.santander.thirdexercise.model.Notification;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("rabbit")
public final class RabbitNotificationProducer implements NotificationProducer {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Autowired
  RabbitNotificationProducer(
      final RabbitTemplate rabbitTemplate,
      final ObjectMapper objectMapper
  ) {
    this.rabbitTemplate = rabbitTemplate;
    this.objectMapper = objectMapper;
  }

  @Override
  public void send(final Notification notification) {
    try {
      this.rabbitTemplate.send(Notification.TOPIC, new Message(this.objectMapper.writeValueAsBytes(notification)));
      log.info("Notification sent with rabbit: {}", notification);
    } catch (final Exception e) {
      log.error("Error sending notification with rabbit: {}", notification, e);
    }
  }
}
