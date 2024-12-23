package me.jarvica.santander.thirdexercise.service;

import lombok.extern.slf4j.Slf4j;
import me.jarvica.santander.thirdexercise.exception.ExceptionResponse;
import me.jarvica.santander.thirdexercise.model.Notification;
import me.jarvica.santander.thirdexercise.repository.NotificationRepository;
import me.jarvica.santander.thirdexercise.service.producer.NotificationProducer;
import me.jarvica.santander.thirdexercise.service.producer.NotificationProducerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Transactional
public class NotificationService {

  private final NotificationRepository repository;
  private final NotificationProducer kafkaProducer;
  private final NotificationProducer rabbitProducer;

  @Autowired
  public NotificationService(
      final NotificationRepository repository,
      final @Qualifier("kafka") NotificationProducer kafkaProducer,
      final @Qualifier("rabbit") NotificationProducer rabbitProducer
  ) {
    this.repository = repository;
    this.kafkaProducer = kafkaProducer;
    this.rabbitProducer = rabbitProducer;
  }

  @Async
  public CompletableFuture<ResponseEntity<Object>> send(
      final NotificationProducerType producerType,
      final Notification notification
  ) {
    return CompletableFuture
        .supplyAsync(() -> {
          switch (producerType) {
            case KAFKA -> this.kafkaProducer.send(notification);
            case RABBIT -> this.rabbitProducer.send(notification);
          }
          return ResponseEntity.ok(null);
        })
        .exceptionally(throwable -> {
          log.error("Error sending notification", throwable);
          return ResponseEntity.internalServerError()
              .body(ExceptionResponse.of("Error sending notification"));
        });
  }

  @Async
  public CompletableFuture<ResponseEntity<Notification>> findById(final Long id) {
    return CompletableFuture.supplyAsync(() -> this.repository.findById(id))
        .thenApply(notification ->
            notification.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build())
        )
        .exceptionally(throwable -> {
          log.error("Error finding notification by id", throwable);
          return ResponseEntity.internalServerError().build();
        });
  }

  @Async
  public CompletableFuture<ResponseEntity<List<Notification>>> findAll() {
    return CompletableFuture.supplyAsync(this.repository::findAll)
        .thenApply(ResponseEntity::ok)
        .exceptionally(throwable -> {
          log.error("Error finding all notifications", throwable);
          return ResponseEntity.internalServerError().build();
        });
  }

  @Async
  public CompletableFuture<ResponseEntity<List<Notification>>> findAllByRecipient(final String recipient) {
    return CompletableFuture.supplyAsync(() -> this.repository.findNotificationsByRecipient(recipient))
        .thenApply(ResponseEntity::ok)
        .exceptionally(throwable -> {
          log.error("Error finding all notifications by recipient", throwable);
          return ResponseEntity.internalServerError().build();
        });
  }
}
