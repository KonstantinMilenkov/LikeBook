package com.likebookapp.model.dto.post;

import java.util.ArrayList;
import java.util.List;

public class PostHomeViewModel {
    private List<PostFromOtherUserDTO> otherPosts;
    private List<PostUserDTO> myPosts;
    private int numberOfPosts;

    public PostHomeViewModel() {
        this.otherPosts = new ArrayList<>();
        this.myPosts = new ArrayList<>();
        this.numberOfPosts = 0;
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public List<PostFromOtherUserDTO> getOtherPosts() {
        return otherPosts;
    }

    public void setOtherPosts(List<PostFromOtherUserDTO> otherPosts) {
        this.otherPosts = otherPosts;
    }

    public List<PostUserDTO> getMyPosts() {
        return myPosts;
    }

    public void setMyPosts(List<PostUserDTO> myPosts) {
        this.myPosts = myPosts;
    }
}
