package com.example.socialnetworkuserserviceapplication.service.impl;

import com.example.socialnetworkuserserviceapplication.exceptions.SocialNetworkErrors;
import com.example.socialnetworkuserserviceapplication.exceptions.SocialNetworkException;
import com.example.socialnetworkuserserviceapplication.repo.IRoleRepository;
import com.example.socialnetworkuserserviceapplication.repo.IUsersRepository;
import com.example.socialnetworkuserserviceapplication.repo.IUsernameChangeLogRepository;
import com.example.socialnetworkuserserviceapplication.repo.entities.UserJPA;
import com.example.socialnetworkuserserviceapplication.repo.entities.UsernameChangeLogJPA;
import com.example.socialnetworkuserserviceapplication.service.IUserService;
import com.example.socialnetworkuserserviceapplication.service.domain.User;
import com.example.socialnetworkuserserviceapplication.service.mapstruct.UserServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {
    private final IUsersRepository userRepository;
    private final IUsernameChangeLogRepository usernameChangeLogRepository;
    private final IRoleRepository roleRepository;
    private final UserServiceMapper mapper;

    @Override
    public List<User> findUserByUsername(String keyword) {
        return mapper.to(userRepository.findByUsernameContaining(keyword));
    }

    @Override
    public User createUser(User user) {
        var userJPA = mapper.from(user);
        roleRepository.findByName("role_user")
                .ifPresent(roleJPA -> {
                    userJPA.setRoles(Arrays.asList(roleJPA));
                });
        return mapper.to(userRepository.save(userJPA));
    }

    @Override
    public User updateUser(User user) {
        var oldUserJPA = userRepository.findById(user.getId()).orElseThrow(() -> {
            throw new SocialNetworkException(SocialNetworkErrors.USER_NOT_FOUND_EXCEPTION);
        });
        var newUserJPA = mapper.merge(oldUserJPA, user);
        var savedUserJPA = userRepository.save(newUserJPA);
        saveUsernameChangeLog(oldUserJPA, newUserJPA);
        return mapper.to(savedUserJPA);
    }

    private void saveUsernameChangeLog(UserJPA oldUser, UserJPA newUser) {
        var changeLog = UsernameChangeLogJPA.builder()
                .userID(oldUser.getId())
                .oldUserName(oldUser.getUsername())
                .newUserName(newUser.getUsername())
                .build();
        usernameChangeLogRepository.save(changeLog);
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.findById(userId).ifPresentOrElse(userJPA -> {
            userJPA.setEnabled(false);
            userRepository.save(userJPA);
        }, () -> {
            throw new SocialNetworkException(SocialNetworkErrors.USER_NOT_FOUND_EXCEPTION);
        });
    }
}
