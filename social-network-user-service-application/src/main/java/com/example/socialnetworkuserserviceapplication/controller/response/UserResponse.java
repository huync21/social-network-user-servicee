package com.example.socialnetworkuserserviceapplication.controller.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
//    UUID uuid;
    Integer id;
    String email;
    String username;
    String avatar;
    String description;
}
