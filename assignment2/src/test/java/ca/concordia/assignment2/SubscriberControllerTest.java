package ca.concordia.assignment2;

import ca.concordia.assignment2.controller.SubscriberController;
import ca.concordia.assignment2.entities.Subscriber;
import ca.concordia.assignment2.repositories.SubscriberRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private SubscriberController subscriberController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriberController).build();
    }

    @Test
    void testAddSubscriber() throws Exception {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(new ObjectId());
        subscriber.setName("John Doe");
        subscriber.setEmail("john.doe@example.com");
        subscriber.setPhone("123-456-7890");

        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(subscriber);

        mockMvc.perform(post("/subscribers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"phone\":\"123-456-7890\"}"))
                .andExpect(status().isOk());

        verify(subscriberRepository, times(1)).save(any(Subscriber.class));
    }

    @Test
    void testGetSubscriberById() throws Exception {
        ObjectId subscriberId = new ObjectId();
        Subscriber subscriber = new Subscriber();
        subscriber.setId(subscriberId);
        subscriber.setName("John Doe");

        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.of(subscriber));

        mockMvc.perform(get("/subscribers/{id}", subscriberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(subscriberRepository, times(1)).findById(subscriberId);
    }

    @Test
    void testUpdateSubscriber() throws Exception {
        ObjectId subscriberId = new ObjectId();
        Subscriber subscriber = new Subscriber();
        subscriber.setId(subscriberId);
        subscriber.setName("John Doe");

        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.of(subscriber));
        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(subscriber);

        mockMvc.perform(put("/subscribers/{id}", subscriberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Jane Doe\",\"email\":\"jane.doe@example.com\",\"phone\":\"987-654-3210\"}"))
                .andExpect(status().isOk());

        verify(subscriberRepository, times(1)).findById(subscriberId);
        verify(subscriberRepository, times(1)).save(any(Subscriber.class));
    }

    @Test
    void testDeleteSubscriber() throws Exception {
        ObjectId subscriberId = new ObjectId();

        doNothing().when(subscriberRepository).deleteById(subscriberId);

        mockMvc.perform(delete("/subscribers/{id}", subscriberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(subscriberRepository, times(1)).deleteById(subscriberId);
    }
}
