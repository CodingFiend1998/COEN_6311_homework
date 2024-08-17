package ca.concordia.assignment2.repositories;


import ca.concordia.assignment2.entities.Subscriber;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriberRepository extends MongoRepository<Subscriber, ObjectId> {
}
