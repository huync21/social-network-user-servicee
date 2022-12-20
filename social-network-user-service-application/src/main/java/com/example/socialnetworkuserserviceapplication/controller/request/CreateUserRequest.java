package com.example.socialnetworkuserserviceapplication.controller.request;

import com.example.socialnetworkuserserviceapplication.common.annotation.ValidPassword;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @NotBlank
    String username;
    @NotBlank
    @ValidPassword
    String password;
    String avatar;
    String description;
}
