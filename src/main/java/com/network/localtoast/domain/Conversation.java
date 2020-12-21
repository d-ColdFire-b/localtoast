package com.network.localtoast.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(max = 255, message = "Title is too long (more than 255)")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    private Message last;



    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_conversations",
            joinColumns = { @JoinColumn(name = "chat_id") },
            inverseJoinColumns = { @JoinColumn(name = "participant_id") }
    )
    private Set<User> participants = new HashSet<>();



    public Conversation() {
    }

    public Conversation(@Length(max = 255, message = "Title is too long (more than 255)") String title) {
        this.title = title;
    }



    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Message getLast() { return last; }

    public void setLast(Message last) { this.last = last; }

    public List<Message> getMessages() { return messages; }

    public void setMessages(List<Message> messages) { this.messages = messages; }

    public Set<User> getParticipants() { return participants; }

    public void setParticipants(Set<User> participants) { this.participants = participants; }

}
