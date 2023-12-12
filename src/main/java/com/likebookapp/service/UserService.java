package com.likebookapp.service;

import com.likebookapp.model.dto.user.UserLoginBindingModel;
import com.likebookapp.model.dto.user.UserRegisterBindingModel;
import com.likebookapp.model.entity.User;

public interface UserService {

    boolean register(UserRegisterBindingModel userRegisterBindingModel);
    boolean login(UserLoginBindingModel userLoginBindingModel);
    void logout();
    User findByUsername(String username);
    User findUserById(Long id);
}
