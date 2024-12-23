package me.jarvica.santander.thirdexercise.repository;

import me.jarvica.santander.thirdexercise.DatabaseBaseTest;
import me.jarvica.santander.thirdexercise.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
class NotificationRepositoryTest extends DatabaseBaseTest {

  @Autowired
  private NotificationRepository repository;

  @Test
  public void testFindNotificationsByRecipient() {
    this.repository.save(Notification.builder().recipient("test").build());
    this.repository.save(Notification.builder().recipient("test").build());
    assertSame(this.repository.findNotificationsByRecipient("test").size(), 2);
  }

}
