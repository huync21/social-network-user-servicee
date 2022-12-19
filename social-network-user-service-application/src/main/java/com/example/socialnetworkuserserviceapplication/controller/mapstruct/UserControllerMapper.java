package com.example.socialnetworkuserserviceapplication.controller.mapstruct;

import com.example.socialnetworkuserserviceapplication.service.domain.User;
import org.mapstruct.Mapper;
import com.example.socialnetworkuserserviceapplication.controller.request.UserRequest;
import com.example.socialnetworkuserserviceapplication.controller.response.UserResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserControllerMapper {
    User from(UserRequest request);
    UserResponse to(User user);
    List<UserResponse> to(List<User> user);
}
