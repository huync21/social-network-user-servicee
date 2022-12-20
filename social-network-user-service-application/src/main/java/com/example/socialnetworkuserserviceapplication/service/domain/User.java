package com.example.socialnetworkuserserviceapplication.service.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    UUID id;
//    Integer id;
    String username;
    String password;
    String avatar;
    String description;
    Boolean enabled;
    List<Role> roles;
}
