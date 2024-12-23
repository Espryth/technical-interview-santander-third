package me.jarvica.santander.thirdexercise.config;

import me.jarvica.santander.thirdexercise.model.Notification;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Bean
  Queue queue() {
    return new Queue(Notification.TOPIC, false);
  }

}
