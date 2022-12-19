package com.example.socialnetworkuserserviceapplication.controller;

import com.example.socialnetworkuserserviceapplication.common.BaseResponse;
import com.example.socialnetworkuserserviceapplication.controller.mapstruct.UserControllerMapper;
import com.example.socialnetworkuserserviceapplication.controller.request.UserRequest;
import com.example.socialnetworkuserserviceapplication.controller.response.UserResponse;
import com.example.socialnetworkuserserviceapplication.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

import static com.example.socialnetworkuserserviceapplication.common.BaseUri.USER_SERVICE.USERS;
import static com.example.socialnetworkuserserviceapplication.common.BaseUri.USER_SERVICE.V1;

@RestController
@RequiredArgsConstructor
@RequestMapping(V1 + USERS)
public class UserController {
    private final IUserService userService;
    private final UserControllerMapper userControllerMapper;

    @GetMapping
    public BaseResponse<List<UserResponse>> findUserByUsername(@RequestParam String keyword) {
        return BaseResponse.ofSucceeded(userControllerMapper.to(userService.findUserByUsername(keyword)));
    }

    @PostMapping
    public BaseResponse<UserResponse> createUser(@Validated @RequestBody UserRequest userRequest){
        return BaseResponse.ofSucceeded(userControllerMapper.to(userService.createUser(userControllerMapper.from(userRequest))));
    }

    @PutMapping
    public BaseResponse<UserResponse> updateUser(@Validated @RequestBody UserRequest userRequest) {
        return BaseResponse.ofSucceeded(userControllerMapper.to(userService.updateUser(userControllerMapper.from(userRequest))));
    }

    @DeleteMapping
    public BaseResponse<Void> deleteUser(@RequestParam Integer userId){
        userService.deleteUser(userId);
        return BaseResponse.ofSucceeded();
    }
}
