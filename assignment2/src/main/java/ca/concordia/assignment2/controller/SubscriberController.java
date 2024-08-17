package ca.concordia.assignment2.controller;

import ca.concordia.assignment2.entities.Subscriber;
import ca.concordia.assignment2.repositories.SubscriberRepository;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberRepository subscriberRepository;

    public SubscriberController(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @GetMapping
    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Subscriber> getSubscriberById(@PathVariable ObjectId id) {
        return subscriberRepository.findById(id);
    }

    @PostMapping
    public Subscriber addSubscriber(@RequestBody Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    @PutMapping("/{id}")
    public Subscriber updateSubscriber(@PathVariable ObjectId id, @RequestBody Subscriber updatedSubscriber) {
        Optional<Subscriber> existingSubscriberOpt = subscriberRepository.findById(id);

        if (existingSubscriberOpt.isPresent()) {
            Subscriber existingSubscriber = existingSubscriberOpt.get();
            existingSubscriber.setName(updatedSubscriber.getName());
            existingSubscriber.setEmail(updatedSubscriber.getEmail());
            existingSubscriber.setPhone(updatedSubscriber.getPhone());
            existingSubscriber.setSubscriptions(updatedSubscriber.getSubscriptions());
            return subscriberRepository.save(existingSubscriber);
        } else {
            throw new RuntimeException("Subscriber not found");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSubscriber(@PathVariable ObjectId id) {
        subscriberRepository.deleteById(id);
    }
}
