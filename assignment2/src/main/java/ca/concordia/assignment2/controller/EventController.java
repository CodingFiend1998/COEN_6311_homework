package ca.concordia.assignment2.controller;


import ca.concordia.assignment2.entities.Event;
import ca.concordia.assignment2.entities.Subscriber;
import ca.concordia.assignment2.repositories.EventRepository;
import ca.concordia.assignment2.repositories.SubscriberRepository;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;
    private final SubscriberRepository subscriberRepository;

    public EventController(EventRepository eventRepository, SubscriberRepository subscriberRepository) {
        this.eventRepository = eventRepository;
        this.subscriberRepository = subscriberRepository;
    }

    @PostMapping("/{eventId}/subscribe/{subscriberId}")
    public Event subscribeToEvent(@PathVariable ObjectId eventId, @PathVariable ObjectId subscriberId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findById(subscriberId);

        if (optionalEvent.isPresent() && optionalSubscriber.isPresent()) {
            Event event = optionalEvent.get();
            Event.Attendee attendee = new Event.Attendee();
            attendee.setSubscriberId(subscriberId);
            attendee.setStatus("confirmed");

            event.getAttendees().add(attendee);
            eventRepository.save(event);

            Subscriber subscriber = optionalSubscriber.get();
            Subscriber.Subscription subscription = new Subscriber.Subscription();
            subscription.setEventId(eventId);
            subscription.setStatus("confirmed");

            subscriber.getSubscriptions().add(subscription);
            subscriberRepository.save(subscriber);

            return event;
        } else {
            throw new RuntimeException("Event or Subscriber not found");
        }
    }

    @PostMapping("/{eventId}/publish")
    public String publishEvent(@PathVariable ObjectId eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.getAttendees().forEach(attendee -> {
                Optional<Subscriber> optionalSubscriber = subscriberRepository.findById(attendee.getSubscriberId());
                optionalSubscriber.ifPresent(subscriber -> {
                    System.out.println("Event Summary for Subscriber " + subscriber.getName() + ":");
                    System.out.println("Event Name: " + event.getName());
                    System.out.println("Event Date: " + event.getDate());
                    System.out.println("Event Description: " + event.getDescription());
                });
            });

            return "Event published successfully!";
        } else {
            throw new RuntimeException("Event not found");
        }
    }
}
