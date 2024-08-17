package ca.concordia.assignment2.services;

import ca.concordia.assignment2.entities.Event;
import org.bson.types.ObjectId;

    public interface EventService {
        Event createEvent(Event event);
        void subscribeToEvent(ObjectId eventId, ObjectId subscriberId);
        void publishEvent(ObjectId eventId);
    }