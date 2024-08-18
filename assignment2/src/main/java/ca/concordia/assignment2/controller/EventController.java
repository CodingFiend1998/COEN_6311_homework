package ca.concordia.assignment2.controller;

import ca.concordia.assignment2.services.EventService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/{eventId}/subscribe/{subscriberId}")
    public ResponseEntity<Void> subscribeToEvent(@PathVariable ObjectId eventId, @PathVariable ObjectId subscriberId) {
        eventService.subscribeToEvent(eventId, subscriberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{eventId}/publish")
    public ResponseEntity<Void> publishEvent(@PathVariable ObjectId eventId) {
        eventService.publishEvent(eventId);
        return ResponseEntity.ok().build();
    }
}
