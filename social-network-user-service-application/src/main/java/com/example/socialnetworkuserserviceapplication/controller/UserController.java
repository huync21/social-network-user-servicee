package com.example.socialnetworkuserserviceapplication.controller;

import com.example.socialnetworkuserserviceapplication.common.BaseResponse;
import com.example.socialnetworkuserserviceapplication.controller.mapstruct.UserControllerMapper;
import com.example.socialnetworkuserserviceapplication.controller.request.ChangePasswordRequest;
import com.example.socialnetworkuserserviceapplication.controller.request.CreateUserRequest;
import com.example.socialnetworkuserserviceapplication.controller.request.RoleRequest;
import com.example.socialnetworkuserserviceapplication.controller.request.UpdateUserRequest;
import com.example.socialnetworkuserserviceapplication.controller.response.UserResponse;
import com.example.socialnetworkuserserviceapplication.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @PostMapping("/authorize")
    public BaseResponse<UserResponse> authorizeUser(@RequestBody RoleRequest request){
        var userResponse = userControllerMapper.to(userService.authorizeRole(request));
        return BaseResponse.ofSucceeded(userResponse);
    }
    @GetMapping
    public BaseResponse<List<UserResponse>> findUserByUsername(@RequestParam String keyword) {
        return BaseResponse.ofSucceeded(userControllerMapper.to(userService.findUserByUsername(keyword)));
    }

    @PostMapping
    public BaseResponse<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        return BaseResponse.ofSucceeded(userControllerMapper.to(userService.createUser(userControllerMapper.from(createUserRequest))));
    }

    @PutMapping
    public BaseResponse<UserResponse> updateUser(@Valid @RequestBody UpdateUserRequest createUserRequest) {
        return BaseResponse.ofSucceeded(userControllerMapper.to(userService.updateUser(userControllerMapper.from(createUserRequest))));
    }

    @DeleteMapping
    public BaseResponse<Void> deleteUser(@RequestParam UUID userId){
        userService.deleteUser(userId);
        return BaseResponse.ofSucceeded();
    }

    @PutMapping("/change-password")
    public BaseResponse<Void> changeUserPassword(@Valid @RequestBody ChangePasswordRequest request){
        userService.changePassword(request);
        return BaseResponse.ofSucceeded();
    }

}
