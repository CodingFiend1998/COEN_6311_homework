package ca.concordia.assignment2.controller;

import ca.concordia.assignment2.entities.Subscriber;
import ca.concordia.assignment2.services.SubscriberService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        return ResponseEntity.ok(subscriberService.getAllSubscribers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscriber> getSubscriberById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(subscriberService.getSubscriberById(id));
    }

    @PostMapping
    public ResponseEntity<Subscriber> addSubscriber(@RequestBody Subscriber subscriber) {
        return ResponseEntity.ok(subscriberService.addSubscriber(subscriber));
    }
}
