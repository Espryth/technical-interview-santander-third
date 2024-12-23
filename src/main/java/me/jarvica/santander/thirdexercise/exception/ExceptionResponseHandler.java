package me.jarvica.santander.thirdexercise.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public final class ExceptionResponseHandler {

  @ExceptionHandler(Exception.class)
  ResponseEntity<ExceptionResponse> handleException(final Exception e) {
    log.error("An internal error occurred", e);
    return ResponseEntity.internalServerError()
        .body(ExceptionResponse.of("An internal error occurred", e.getMessage()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<ExceptionResponse> handleValidationException(final ConstraintViolationException e) {
    log.error("Validation error", e);
    final var details = e.getConstraintViolations().stream()
        .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
        .toArray(String[]::new);
    return ResponseEntity.badRequest()
        .body(ExceptionResponse.of("Validation error", details));
  }
}
