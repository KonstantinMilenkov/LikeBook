package com.likebookapp.service;

import com.likebookapp.model.dto.post.PostAddBindingModel;
import com.likebookapp.model.dto.post.PostHomeViewModel;

public interface PostService {
    boolean add(PostAddBindingModel postAddBindingModel);
    void remove(Long id);
    void likePostsWithId(Long postId, Long userId);

    PostHomeViewModel viewAllPosts();
}
