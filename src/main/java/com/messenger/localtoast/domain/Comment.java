package com.messenger.localtoast.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Write something here")
    @Length(max = 2048, message = "Text is too long (more than 2048)")
    private String text;

    private String filename;

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private User sender;



    public Comment() {
    }

    public Comment(String text, User user) {
        this.text = text;
        this.sender = user;
    }



    public String getSenderName(){
        return sender != null ? sender.getName() : "<none>";
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() { return sender; }

    public void setSender(User author) { this.sender = author; }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public Post getPost() { return post; }

    public void setPost(Post post) { this.post = post; }

}
