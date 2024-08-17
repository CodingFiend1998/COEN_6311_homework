package ca.concordia.assignment2;

import ca.concordia.assignment2.controller.EventController;
import ca.concordia.assignment2.entities.Event;
import ca.concordia.assignment2.services.EventService;
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

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @Mock
    private SubscriberService subscriberService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    void testCreateEvent() throws Exception {
        Event event = new Event();
        event.setId(new ObjectId());
        event.setName("Test Event");
        event.setDate(new Date());

        when(eventService.createEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Event\", \"date\": \"2024-08-16T00:00:00Z\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Event"));
    }

    @Test
    void testSubscribeToEvent() throws Exception {
        ObjectId eventId = new ObjectId();
        ObjectId subscriberId = new ObjectId();

        // If subscribeToEvent returns void, use doNothing()
        doNothing().when(eventService).subscribeToEvent(eventId, subscriberId);

        mockMvc.perform(post("/events/" + eventId + "/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subscriberId\": \"" + subscriberId + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testPublishEvent() throws Exception {
        ObjectId eventId = new ObjectId();

        // If publishEvent returns void, use doNothing()
        doNothing().when(eventService).publishEvent(eventId);

        mockMvc.perform(post("/events/" + eventId + "/publish"))
                .andExpect(status().isOk());
    }

    // Add more tests for other endpoints as needed
}
