package com.example.socialnetworkuserserviceapplication.controller.request;

import com.example.socialnetworkuserserviceapplication.common.annotation.ValidPassword;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {
    @NotNull
    UUID userID;
    @NotBlank
    String oldPassword;
    @NotBlank
    @ValidPassword
    String newPassword;
}
