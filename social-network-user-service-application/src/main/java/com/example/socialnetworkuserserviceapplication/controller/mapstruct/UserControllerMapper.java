package com.example.socialnetworkuserserviceapplication.controller.mapstruct;

import com.example.socialnetworkuserserviceapplication.controller.request.UpdateUserRequest;
import com.example.socialnetworkuserserviceapplication.service.domain.User;
import org.mapstruct.Mapper;
import com.example.socialnetworkuserserviceapplication.controller.request.CreateUserRequest;
import com.example.socialnetworkuserserviceapplication.controller.response.UserResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserControllerMapper {
    User from(CreateUserRequest request);
    User from(UpdateUserRequest request);
    UserResponse to(User user);
    List<UserResponse> to(List<User> user);
}
