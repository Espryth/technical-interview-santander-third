package me.jarvica.santander.thirdexercise.controller;

import me.jarvica.santander.thirdexercise.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private NotificationService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testPublish() throws Exception {
    when(this.service.send(any(), any())).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok().build()));
    this.mockMvc
        .perform(
            post("/notification/publish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "message": "test",
                      "recipient": "test",
                      "producerType": "KAFKA"
                    }
                    """
                )
        )
        .andExpect(status().isOk());
  }
}
