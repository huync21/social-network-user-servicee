package com.example.socialnetworkuserserviceapplication.service;

import com.example.socialnetworkuserserviceapplication.controller.request.ChangePasswordRequest;
import com.example.socialnetworkuserserviceapplication.controller.request.RoleRequest;
import com.example.socialnetworkuserserviceapplication.service.domain.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<User> findUserByUsername(String keyword);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(UUID userId);
    void changePassword(ChangePasswordRequest request);
    User authorizeRole(RoleRequest request);
}
