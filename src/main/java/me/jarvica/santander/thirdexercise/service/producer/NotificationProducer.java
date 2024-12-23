package me.jarvica.santander.thirdexercise.service.producer;

import me.jarvica.santander.thirdexercise.model.Notification;

public sealed interface NotificationProducer permits KafkaNotificationProducer, RabbitNotificationProducer {

  void send(final Notification notification);

}
