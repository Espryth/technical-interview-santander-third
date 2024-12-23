package me.jarvica.santander.thirdexercise.service.consumer;

public sealed interface NotificationConsumer<T> permits KafkaNotificationConsumer, RabbitNotificationConsumer {

  void process(final T notification);

}
