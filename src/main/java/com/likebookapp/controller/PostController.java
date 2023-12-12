package com.likebookapp.controller;

import com.likebookapp.model.dto.post.PostAddBindingModel;
import com.likebookapp.model.entity.User;
import com.likebookapp.service.PostService;
import com.likebookapp.service.UserService;
import com.likebookapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final LoggedUser loggedUser;

    public PostController(PostService postService, UserService userService, LoggedUser loggedUser) {
        this.postService = postService;
        this.userService = userService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/add-post")
    public ModelAndView add(@ModelAttribute("postAddBindingModel")
                                PostAddBindingModel postAddBindingModel){
        if (!loggedUser.isLogged()){
            return new ModelAndView("index");
        }
        return new ModelAndView("post-add");
    }

    @PostMapping("/add-post")
    public ModelAndView add(@ModelAttribute("postAddBindingModel")
                                @Valid PostAddBindingModel postAddBindingModel,
                            BindingResult bindingResult){

        if (!loggedUser.isLogged()){
            return new ModelAndView("index");
        }

        if (bindingResult.hasErrors()){
            return new ModelAndView("post-add");
        }

        boolean isAdded = postService.add(postAddBindingModel);

        if (!isAdded){
            ModelAndView modelAndView = new ModelAndView("post-add");
            modelAndView.addObject("hasAddError",true);
            return modelAndView;
        }

        return new ModelAndView("redirect:/home");
    }
    @PostMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        if (!loggedUser.isLogged()){
            return new ModelAndView("index");
        }

        postService.remove(id);

        return new ModelAndView("redirect:/home");
    }
    @PostMapping("/like/{id}")
    public ModelAndView like(@PathVariable("id") Long id){
        if (!loggedUser.isLogged()){
            return new ModelAndView("index");
        }
        User user = userService.findByUsername(loggedUser.getUsername());

        postService.likePostsWithId(id, user.getId());

        return new ModelAndView("redirect:/home");
    }
}
