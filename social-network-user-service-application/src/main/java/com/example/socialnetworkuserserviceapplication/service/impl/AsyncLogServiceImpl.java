package com.example.socialnetworkuserserviceapplication.service.impl;

import com.example.socialnetworkuserserviceapplication.repo.IUsernameChangeLogRepository;
import com.example.socialnetworkuserserviceapplication.repo.entities.UsernameChangeLogJPA;
import com.example.socialnetworkuserserviceapplication.service.IAsyncLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AsyncLogServiceImpl implements IAsyncLogService {
    private final IUsernameChangeLogRepository usernameChangeLogRepository;

    @Override
    @Async
    public void logUserNamePasswordChange(String oldUsername, String newUsername, UUID userId) {
        if(!oldUsername.equals(newUsername)){
            var changeLog = UsernameChangeLogJPA.builder()
                    .userID(userId.toString())
                    .oldUserName(oldUsername)
                    .newUserName(newUsername)
                    .build();
            usernameChangeLogRepository.save(changeLog);
        }

        // Test async
        try {
            Thread.sleep(10000);
            System.out.println("async worked");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
