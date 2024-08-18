package ca.concordia.assignment2.services;

import ca.concordia.assignment2.entities.Event;
import ca.concordia.assignment2.entities.Subscriber;
import ca.concordia.assignment2.repositories.EventRepository;
import ca.concordia.assignment2.repositories.SubscriberRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final SubscriberRepository subscriberRepository;

    public EventServiceImpl(EventRepository eventRepository, SubscriberRepository subscriberRepository) {
        this.eventRepository = eventRepository;
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public Event createEvent(Event event) {
        return null;
    }

    @Override
    public void subscribeToEvent(ObjectId eventId, ObjectId subscriberId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        Optional<Subscriber> subscriberOpt = subscriberRepository.findById(subscriberId);

        if (eventOpt.isPresent() && subscriberOpt.isPresent()) {
            Event event = eventOpt.get();
            Subscriber subscriber = subscriberOpt.get();

            // Add the subscriber to the event's attendees
            Event.Attendee attendee = new Event.Attendee();
            attendee.setSubscriberId(subscriber.getId());
            attendee.setStatus("confirmed");
            event.getAttendees().add(attendee);

            // Add the event to the subscriber's subscriptions
            Subscriber.Subscription subscription = new Subscriber.Subscription();
            subscription.setEventId(event.getId());
            subscription.setStatus("confirmed");
            subscriber.getSubscriptions().add(subscription);

            eventRepository.save(event);
            subscriberRepository.save(subscriber);
        } else {
            throw new RuntimeException("Event or Subscriber not found");
        }
    }

    @Override
    public void publishEvent(ObjectId eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);

        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();

            // Notify all subscribers
            for (Event.Attendee attendee : event.getAttendees()) {
                Optional<Subscriber> subscriberOpt = subscriberRepository.findById(attendee.getSubscriberId());

                if (subscriberOpt.isPresent()) {
                    Subscriber subscriber = subscriberOpt.get();
                    System.out.println("Notifying subscriber: " + subscriber.getName() + " about event: " + event.getName());
                }
            }
        } else {
            throw new RuntimeException("Event not found");
        }
    }
}
