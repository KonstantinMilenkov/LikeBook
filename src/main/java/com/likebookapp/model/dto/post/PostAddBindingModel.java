package com.likebookapp.model.dto.post;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostAddBindingModel {
    private Long id;

    @Size(min = 2,max = 50, message = "Content length must be between 2 and 50 characters!")
    @NotNull
    private String content;

    @NotNull(message = "You must select a mood!")
    private String mood;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
}
