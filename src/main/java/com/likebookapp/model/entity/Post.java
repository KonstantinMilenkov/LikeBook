package com.likebookapp.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity{
    @Column(nullable = false,columnDefinition = "TEXT")
    private String content;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> userLikes;
    @Column
    private int likes;
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "moods_id")
    private Mood mood;

    public Post() {
    }


    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Set<User> userLikes) {
        this.userLikes = userLikes;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }
}
