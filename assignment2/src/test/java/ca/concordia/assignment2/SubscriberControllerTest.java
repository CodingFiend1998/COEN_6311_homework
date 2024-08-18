package ca.concordia.assignment2;


import ca.concordia.assignment2.controller.SubscriberController;
import ca.concordia.assignment2.entities.Subscriber;
import ca.concordia.assignment2.services.SubscriberService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SubscriberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SubscriberService subscriberService;

    @InjectMocks
    private SubscriberController subscriberController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriberController).build();
    }

    @Test
    void testCreateSubscriber() throws Exception {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(new ObjectId());
        subscriber.setName("John Doe");
        subscriber.setEmail("johndoe@example.com");

        when(subscriberService.createSubscriber(any(Subscriber.class))).thenReturn(subscriber);

        mockMvc.perform(post("/subscribers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"email\": \"johndoe@example.com\", \"phone\": \"123-456-7890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));
    }

    // Add more tests for other endpoints as needed
}
