package ca.concordia.assignment2.repositories;


import ca.concordia.assignment2.entities.Event;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, ObjectId> {
}
