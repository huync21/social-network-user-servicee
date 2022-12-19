package com.example.socialnetworkuserserviceapplication.service.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
//    UUID uuid;
    Integer id;
    String email;
    String username;
    String password;
    String avatar;
    String description;
    boolean enabled;
}
