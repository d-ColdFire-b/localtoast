package com.messenger.localtoast.domain;

import javax.persistence.*;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscriber_id")
    private User subscriber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id")
    private User channel;


    public Subscription() {
    }

    public Subscription(User subscriber, User channel) {
        this.subscriber = subscriber;
        this.channel = channel;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSubscriber() { return subscriber; }

    public void setSubscriber(User subscriber) { this.subscriber = subscriber; }

    public User getChannel() { return channel; }

    public void setChannel(User channel) { this.channel = channel; }

}
