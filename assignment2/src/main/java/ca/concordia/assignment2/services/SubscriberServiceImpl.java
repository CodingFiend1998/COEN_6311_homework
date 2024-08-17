package ca.concordia.assignment2.services;

import ca.concordia.assignment2.entities.Subscriber;
import ca.concordia.assignment2.repositories.SubscriberRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberServiceImpl(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public Subscriber createSubscriber(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    @Override
    public void subscribeToEvent(ObjectId eventId, ObjectId subscriberId) {
        // Implementation here
    }

    @Override
    public List<Subscriber> getAllSubscribers() {
        return List.of();
    }

    @Override
    public Subscriber getSubscriberById(ObjectId id) {
        return null;
    }

    @Override
    public Subscriber addSubscriber(Subscriber subscriber) {
        return null;
    }

    @Override
    public Subscriber updateSubscriber(ObjectId id, Subscriber subscriber) {
        return null;
    }

    @Override
    public void deleteSubscriber(ObjectId id) {

    }

}
