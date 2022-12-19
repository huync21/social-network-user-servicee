package com.example.socialnetworkuserserviceapplication.service;

import com.example.socialnetworkuserserviceapplication.service.domain.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<User> findUserByUsername(String keyword);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Integer userId);
}
