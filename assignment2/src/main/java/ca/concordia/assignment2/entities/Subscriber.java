package ca.concordia.assignment2.entities;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "subscribers")
public class Subscriber {
    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String phone;
    private List<Subscription> subscriptions = new ArrayList<>();

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    // Nested Subscription class with its getters and setters
    public static class Subscription {
        private ObjectId eventId;
        private String status;

        public ObjectId getEventId() {
            return eventId;
        }

        public void setEventId(ObjectId eventId) {
            this.eventId = eventId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
