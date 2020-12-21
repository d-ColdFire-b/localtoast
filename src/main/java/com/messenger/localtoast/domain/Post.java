package com.messenger.localtoast.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Write something here")
    @Length(max = 2048, message = "Text is too long (more than 2048)")
    private String text;

    private String filename;

    private Date date;

    private String access;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;



    public Post() {
    }

    public Post(String text, User user) {
        this.text = text;
        this.creator = user;
    }



    public String getCreatorName(){
        return creator != null ? creator.getName() : "<none>";
    }

    public int getCommentsNumber() { return comments != null ? comments.size() : 0; }



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

    public User getCreator() { return creator; }

    public void setCreator(User author) { this.creator = author; }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }

    public String getAccess() { return access; }

    public void setAccess(String access) { this.access = access; }

}
