package com.example.socialnetworkuserserviceapplication.controller.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
//    UUID uuid;
    Integer id;
    String email;
    String username;
    String password;
    String avatar;
    String description;
    boolean enabled;
}
