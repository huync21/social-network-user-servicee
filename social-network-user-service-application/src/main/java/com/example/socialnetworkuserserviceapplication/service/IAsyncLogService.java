package com.example.socialnetworkuserserviceapplication.service;

import org.springframework.scheduling.annotation.Async;

import java.util.UUID;

public interface IAsyncLogService {
    @Async
    void logUserNamePasswordChange(String oldUsername, String newUsername, UUID userid);
}
