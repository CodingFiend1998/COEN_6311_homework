package ca.concordia.assignment2;

import ca.concordia.assignment2.controller.EventController;
import ca.concordia.assignment2.entities.Event;
import ca.concordia.assignment2.entities.Subscriber;
import ca.concordia.assignment2.repositories.EventRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerTest {
    private MockMvc mockMvc;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    void testSubscribeToEvent() throws Exception {
        ObjectId eventId = new ObjectId();
        ObjectId subscriberId = new ObjectId();

        Event event = new Event();
        event.setId(eventId);

        Subscriber subscriber = new Subscriber();
        subscriber.setId(subscriberId);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.of(subscriber));

        mockMvc.perform(post("/events/{eventId}/subscribe/{subscriberId}", eventId, subscriberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(eventRepository, times(1)).save(any(Event.class));
        verify(subscriberRepository, times(1)).save(any(Subscriber.class));
    }

    @Test
    void testPublishEvent() throws Exception {
        ObjectId eventId = new ObjectId();
        ObjectId subscriberId = new ObjectId();

        Event event = new Event();
        event.setId(eventId);

        Event.Attendee attendee = new Event.Attendee();
        attendee.setSubscriberId(subscriberId);
        event.getAttendees().add(attendee);

        Subscriber subscriber = new Subscriber();
        subscriber.setId(subscriberId);
        subscriber.setName("John Doe");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.of(subscriber));

        mockMvc.perform(post("/events/{eventId}/publish", eventId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(eventRepository, times(1)).findById(eventId);
        verify(subscriberRepository, times(1)).findById(subscriberId);
    }

}