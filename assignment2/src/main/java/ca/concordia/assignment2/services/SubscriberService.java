package ca.concordia.assignment2.services;

import ca.concordia.assignment2.entities.Subscriber;
import org.bson.types.ObjectId;

import java.util.List;

public interface SubscriberService {
    List<Subscriber> getAllSubscribers();
    Subscriber getSubscriberById(ObjectId id);
    Subscriber addSubscriber(Subscriber subscriber);
    Subscriber updateSubscriber(ObjectId id, Subscriber subscriber);
    void deleteSubscriber(ObjectId id);
    Subscriber createSubscriber(Subscriber subscriber);
    void subscribeToEvent(ObjectId eventId, ObjectId subscriberId);
}

