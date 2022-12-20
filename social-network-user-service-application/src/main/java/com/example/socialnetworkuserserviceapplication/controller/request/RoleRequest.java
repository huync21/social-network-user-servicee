package com.example.socialnetworkuserserviceapplication.controller.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    @NotNull
    UUID userId;
    @NotEmpty
    List<String> roles;
}
