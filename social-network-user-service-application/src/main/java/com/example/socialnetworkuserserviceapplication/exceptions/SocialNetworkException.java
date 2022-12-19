package com.example.socialnetworkuserserviceapplication.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialNetworkException extends RuntimeException{
    SocialNetworkBusinessError error;

    public SocialNetworkException(SocialNetworkBusinessError error) {
        super(error.getMessage());
        this.error = error;
    }

    public SocialNetworkException(SocialNetworkBusinessError error, String message) {
        super(message);
        this.error = error;
    }

    public SocialNetworkException(SocialNetworkBusinessError error, String message, Throwable cause){
        super(message, cause);
        this.error = error;
    }
}
