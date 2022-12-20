package com.example.socialnetworkuserserviceapplication.exceptions;

import org.springframework.http.HttpStatus;

public class SocialNetworkErrors {
    private SocialNetworkErrors(){
    }

    /**
     * 400
     */
    public static final SocialNetworkBusinessError USER_NOT_FOUND_EXCEPTION = new SocialNetworkBusinessError(400000, "User not found", HttpStatus.BAD_REQUEST);
    public static final SocialNetworkBusinessError INVALID_PARAMETERS = new SocialNetworkBusinessError(400001, "Invalid Parameter", HttpStatus.BAD_REQUEST);
    public static final SocialNetworkBusinessError USER_NAME_ALREADY_EXISTED = new SocialNetworkBusinessError(400002, "User name already existed", HttpStatus.BAD_REQUEST);
    public static final SocialNetworkBusinessError OLD_PASSWORD_INCORRECT = new SocialNetworkBusinessError(400003, "Your old password is incorrect", HttpStatus.BAD_REQUEST);
    public static final SocialNetworkBusinessError ROLE_DOESNT_EXIST = new SocialNetworkBusinessError(400004, "Role doesn't exist", HttpStatus.BAD_REQUEST);

    /**
     * 401
     */
    public static final SocialNetworkBusinessError UNAUTHORIZED = new SocialNetworkBusinessError(401, "Unauthorized client", HttpStatus.UNAUTHORIZED);
}
