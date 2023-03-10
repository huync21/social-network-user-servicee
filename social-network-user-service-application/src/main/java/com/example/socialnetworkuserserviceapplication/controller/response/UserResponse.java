package com.example.socialnetworkuserserviceapplication.controller.response;

import com.example.socialnetworkuserserviceapplication.service.domain.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID id;
//    Integer id;
    String username;
    String avatar;
    String description;
    List<Role> roles;
}
