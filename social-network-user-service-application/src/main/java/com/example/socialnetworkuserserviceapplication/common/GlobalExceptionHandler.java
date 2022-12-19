package com.example.socialnetworkuserserviceapplication.common;

import com.example.socialnetworkuserserviceapplication.exceptions.SocialNetworkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({SocialNetworkException.class})
    public ResponseEntity<BaseResponse<Void>> handleBusinessException(SocialNetworkException e) {
        var socialNetworkBusinessError = e.getError();
        var data = BaseResponse.ofFailed(socialNetworkBusinessError, socialNetworkBusinessError.getMessage());
        return ResponseEntity.status(socialNetworkBusinessError.getHttpStatus()).body(data);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleUnwantedException(Exception e) {
        e.printStackTrace();
        var data = BaseResponse.ofFailed(e);
        return ResponseEntity.status(500).body(data);
    }
}
