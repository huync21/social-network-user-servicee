package com.example.socialnetworkuserserviceapplication.exceptions;

import org.springframework.http.HttpStatus;

public class SocialNetworkErrors {
    private SocialNetworkErrors(){
    }

    /**
     * 400
     */
    public static final SocialNetworkBusinessError USER_NOT_FOUND_EXCEPTION = new SocialNetworkBusinessError(400000, "User not found", HttpStatus.BAD_REQUEST);
}
