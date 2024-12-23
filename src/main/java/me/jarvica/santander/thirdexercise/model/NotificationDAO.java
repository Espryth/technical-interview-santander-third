package me.jarvica.santander.thirdexercise.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.jarvica.santander.thirdexercise.service.producer.NotificationProducerType;

public record NotificationDAO(
    @NotNull NotificationProducerType producerType,
    @NotNull @NotBlank String recipient,
    @NotNull @NotBlank String message
) {
}
