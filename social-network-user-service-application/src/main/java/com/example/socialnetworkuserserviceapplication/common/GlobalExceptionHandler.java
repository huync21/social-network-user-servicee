package com.example.socialnetworkuserserviceapplication.common;

import com.example.socialnetworkuserserviceapplication.exceptions.SocialNetworkErrors;
import com.example.socialnetworkuserserviceapplication.exceptions.SocialNetworkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({SocialNetworkException.class})
    public ResponseEntity<BaseResponse<Void>> handleBusinessException(SocialNetworkException e) {
        var socialNetworkBusinessError = e.getError();
        var data = BaseResponse.ofFailed(socialNetworkBusinessError, e.getMessage());
        return ResponseEntity.status(socialNetworkBusinessError.getHttpStatus()).body(data);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleUnwantedException(Exception e) {
        e.printStackTrace();
        var data = BaseResponse.ofFailed(e);
        return ResponseEntity.status(500).body(data);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleInvalidParameterException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> (fieldError.getField()+": "+fieldError.getDefaultMessage()+"\n"))
                .reduce("", (pre,next)->pre+next);
        var data = BaseResponse.ofFailed(SocialNetworkErrors.INVALID_PARAMETERS,message);
        return ResponseEntity.status(400).body(data);
    }
}
