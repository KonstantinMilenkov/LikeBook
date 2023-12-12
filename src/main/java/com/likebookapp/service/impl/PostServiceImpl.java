package com.likebookapp.service.impl;

import com.likebookapp.model.dto.post.PostAddBindingModel;
import com.likebookapp.model.dto.post.PostFromOtherUserDTO;
import com.likebookapp.model.dto.post.PostHomeViewModel;
import com.likebookapp.model.dto.post.PostUserDTO;
import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;
import com.likebookapp.model.enums.MoodName;
import com.likebookapp.repository.PostRepository;
import com.likebookapp.service.MoodService;
import com.likebookapp.service.PostService;
import com.likebookapp.service.UserService;
import com.likebookapp.util.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final MoodService moodService;
    private final LoggedUser loggedUser;

    public PostServiceImpl(PostRepository postRepository, UserService userService, MoodService moodService, LoggedUser loggedUser) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.moodService = moodService;
        this.loggedUser = loggedUser;
    }


    @Override
    public boolean add(PostAddBindingModel postAddBindingModel) {
        MoodName moodName = MoodName.valueOf(postAddBindingModel.getMood());
        Mood mood = moodService.findByMood(moodName);

        if (mood != null) {
            Post post = new Post();
            String username = loggedUser.getUsername();
            User user = userService.findByUsername(username);

            createPost(postAddBindingModel, post, user, mood);

            postRepository.save(post);

            user.getPosts().add(post);
            return true;
        }

        return false;
    }

    @Override
    public void remove(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostHomeViewModel viewAllPosts() {
        Optional<List<Post>> allByUserUsername = postRepository.findAllByUserUsername(loggedUser.getUsername());
        Optional<List<Post>> otherPeoplePosts = postRepository.findAllWhereUserIsNotCreator(loggedUser.getUsername());

        List<PostUserDTO> collectOfUserPosts = new ArrayList<>();

        if (allByUserUsername.isPresent()) {
            collectOfUserPosts = allByUserUsername.get().stream()
                    .map(this::toPostUserDTO)
                    .toList();
        }

        List<PostFromOtherUserDTO> collectAllOtherPosts = new ArrayList<>();

        if (otherPeoplePosts.isPresent()) {
            collectAllOtherPosts = otherPeoplePosts.get().stream()
                    .map(this::toPostFromOtherUserDTO)
                    .toList();
        }

        PostHomeViewModel postHomeViewModel = new PostHomeViewModel();

        postHomeViewModel.setMyPosts(collectOfUserPosts);
        postHomeViewModel.setOtherPosts(collectAllOtherPosts);
        postHomeViewModel.setNumberOfPosts(otherPeoplePosts.get().size());

        return postHomeViewModel;
    }

    private static void createPost(PostAddBindingModel postAddBindingModel, Post post, User user, Mood mood) {
        post.setId(postAddBindingModel.getId());
        post.setUser(user);
        post.setMood(mood);
        post.setContent(postAddBindingModel.getContent());
        post.setLikes(0);
    }

    @Override
    public void likePostsWithId(Long postId, Long userId) {
        Post post = postRepository.findPostById(postId);
        User user = userService.findUserById(userId);

        boolean containsUser = false;
        for (User userLike : post.getUserLikes()) {
            String currentUsername = userLike.getUsername();

            if (currentUsername.equals(user.getUsername())) {
                containsUser = true;

                post.getUserLikes().removeIf(u -> u.getUsername().equals(user.getUsername()));
                post.setLikes(post.getLikes() - 1);
                user.getLikedPosts().remove(post);

                postRepository.save(post);

                break;
            }
        }


        if (!containsUser || post.getUserLikes().isEmpty()) {
            post.getUserLikes().add(user);
            post.setLikes(post.getLikes() + 1);
            user.getLikedPosts().add(post);

            postRepository.save(post);
        }

    }

    private PostUserDTO toPostUserDTO(Post post) {
        PostUserDTO postUserDTO = new PostUserDTO();

        postUserDTO.setId(post.getId());
        postUserDTO.setContent(post.getContent());
        postUserDTO.setMood(post.getMood().getName().name());
        postUserDTO.setLikes(post.getLikes());

        return postUserDTO;
    }

    private PostFromOtherUserDTO toPostFromOtherUserDTO(Post post) {
        PostFromOtherUserDTO postFromOtherUserDTO = new PostFromOtherUserDTO();

        postFromOtherUserDTO.setId(post.getId());
        postFromOtherUserDTO.setContent(post.getContent());
        postFromOtherUserDTO.setMood(post.getMood().getName().name());
        postFromOtherUserDTO.setUsername(post.getUser().getUsername());
        postFromOtherUserDTO.setNumberOfLikes(post.getLikes());

        return postFromOtherUserDTO;
    }
}
