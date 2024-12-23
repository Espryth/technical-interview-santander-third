package me.jarvica.santander.thirdexercise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notifications")
public final class Notification {

  public static final String TOPIC = "notifications";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String recipient;

  private String message;

  @Column(name = "published_at", updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date publishedAt;

}
