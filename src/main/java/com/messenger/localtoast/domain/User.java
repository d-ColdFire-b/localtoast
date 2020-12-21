package com.messenger.localtoast.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Length(max = 255, message = "Username is too long (more than 255)")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Length(max = 255, message = "Password is too long (more than 255)")
    private String password;

    private boolean active;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @NotBlank(message = "Name cannot be empty")
    @Length(max = 255, message = "Name is too long (more than 255)")
    private String name;

    @NotBlank(message = "Last name cannot be empty")
    @Length(max = 255, message = "Last name is too long (more than 255)")
    private String lastname;

    @Length(max = 255, message = "Nickname is too long (more than 255)")
    private String nickname;

    @Length(max = 255, message = "Picture name is too long (more than 255)")
    private String picname;

    @Length(max = 255, message = "City name is too long (more than 255)")
    private String city;

    @Length(max = 255, message = "Interests description is too long (more than 255)")
    private String interests;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<User> friends;


    /*@ManyToMany(mappedBy = "friendsWith", fetch = FetchType.EAGER)
    private List<User> friends = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_friends",
            joinColumns = { @JoinColumn(name = "first_id") },
            inverseJoinColumns = { @JoinColumn(name = "second_id") }
    )
    private List<User> friendsWith = new ArrayList<>();*/


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subscription> outgoingRequests;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subscription> pendingRequests;





    public boolean isAdmin(){
        return roles.contains(Role.ADMIN);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getActivationCode() { return activationCode; }

    public void setActivationCode(String activationCode) { this.activationCode = activationCode; }

    public List<Message> getMessages() { return messages; }

    public void setMessages(List<Message> messages) { this.messages = messages; }

    public List<Post> getPosts() { return posts; }

    public void setPosts(List<Post> posts) { this.posts = posts; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    /*public List<Conversation> getConversations() { return conversations; }

    public void setConversations(List<Conversation> conversations) { this.conversations = conversations; }*/

    public String getPicname() {
        if (picname == null || StringUtils.isEmpty(picname)) {
            return "nopic.jpg";
        } else return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getInterests() { return interests; }

    public void setInterests(String interests) { this.interests = interests; }

    public List<User> getFriends() { return friends; }

    public void setFriends(List<User> friends) { this.friends = friends; }

    public List<Subscription> getOutgoingRequests() { return outgoingRequests; }

    public void setOutgoingRequests(List<Subscription> outgoingRequests) { this.outgoingRequests = outgoingRequests; }

    public List<Subscription> getPendingRequests() { return pendingRequests; }

    public void setPendingRequests(List<Subscription> pendingRequests) { this.pendingRequests = pendingRequests; }

}
