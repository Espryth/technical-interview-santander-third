package me.jarvica.santander.thirdexercise.controller;

import jakarta.validation.Valid;
import me.jarvica.santander.thirdexercise.model.Notification;
import me.jarvica.santander.thirdexercise.model.NotificationDAO;
import me.jarvica.santander.thirdexercise.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/notification")
public final class NotificationController {

  private final NotificationService service;

  @Autowired
  NotificationController(final NotificationService service) {
    this.service = service;
  }

  @PostMapping("/publish")
  CompletableFuture<ResponseEntity<Object>> publish(final @Valid @RequestBody NotificationDAO notification) {
    return this.service.send(
        notification.producerType(),
        Notification.builder()
            .message(notification.message())
            .recipient(notification.recipient())
            .build()
    );
  }

  @GetMapping("/{id}")
  CompletableFuture<ResponseEntity<Notification>> find(final @PathVariable("id") Long id) {
    return this.service.findById(id);
  }

  @GetMapping()
  CompletableFuture<ResponseEntity<List<Notification>>> findAll() {
    return this.service.findAll();
  }

  @GetMapping("/recipient/{recipient}")
  CompletableFuture<ResponseEntity<List<Notification>>> findByRecipient(final @PathVariable("recipient") String recipient) {
    return this.service.findAllByRecipient(recipient);
  }
}
