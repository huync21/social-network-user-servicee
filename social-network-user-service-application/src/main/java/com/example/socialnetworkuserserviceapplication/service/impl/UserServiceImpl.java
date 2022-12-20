package com.example.socialnetworkuserserviceapplication.service.impl;

import com.example.socialnetworkuserserviceapplication.controller.request.ChangePasswordRequest;
import com.example.socialnetworkuserserviceapplication.controller.request.RoleRequest;
import com.example.socialnetworkuserserviceapplication.exceptions.SocialNetworkErrors;
import com.example.socialnetworkuserserviceapplication.exceptions.SocialNetworkException;
import com.example.socialnetworkuserserviceapplication.repo.IRoleRepository;
import com.example.socialnetworkuserserviceapplication.repo.IUsernameChangeLogRepository;
import com.example.socialnetworkuserserviceapplication.repo.IUsersRepository;
import com.example.socialnetworkuserserviceapplication.repo.entities.UsernameChangeLogJPA;
import com.example.socialnetworkuserserviceapplication.service.IAsyncLogService;
import com.example.socialnetworkuserserviceapplication.service.IUserService;
import com.example.socialnetworkuserserviceapplication.service.domain.User;
import com.example.socialnetworkuserserviceapplication.service.mapstruct.UserServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {
    private final IUsersRepository userRepository;
    private final IUsernameChangeLogRepository usernameChangeLogRepository;
    private final IRoleRepository roleRepository;
    private final UserServiceMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final IAsyncLogService asyncLogService;

    @Override
    public List<User> findUserByUsername(String keyword) {
        return mapper.to(userRepository.findByUsernameContainingAndEnabled(keyword, true));
    }

    @Override
    public User createUser(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(userJPA -> {
            throw new SocialNetworkException(SocialNetworkErrors.USER_NAME_ALREADY_EXISTED);
        });
        var userJPA = mapper.from(user);
        userJPA.setPassword(passwordEncoder.encode(userJPA.getPassword()));
        userJPA.setEnabled(true);
        roleRepository.findByName("role_user")
                .ifPresent(roleJPA -> {
                    userJPA.setRoles(Arrays.asList(roleJPA));
                });
        return mapper.to(userRepository.save(userJPA));
    }

    @Override
    public User updateUser(User user) {
        var userId = user.getId();
        var oldUserJPA = userRepository.findById(userId).orElseThrow(() -> {
            throw new SocialNetworkException(SocialNetworkErrors.USER_NOT_FOUND_EXCEPTION);
        });
        String oldUserName = oldUserJPA.getUsername();
        String newUserName = user.getUsername();

        var newUserJPA = mapper.merge(oldUserJPA, user);
        var savedUserJPA = userRepository.save(newUserJPA);

        // log username change asynchronously
        // split async function to another service because we cannot call async function in the same class
        asyncLogService.logUserNamePasswordChange(oldUserName, newUserName, userId);

        return mapper.to(savedUserJPA);
    }

    @Override
    public void deleteUser(@RequestParam UUID userId) {
        userRepository.findById(userId).ifPresentOrElse(userJPA -> {
            userJPA.setEnabled(false);
            userRepository.save(userJPA);
        }, () -> {
            throw new SocialNetworkException(SocialNetworkErrors.USER_NOT_FOUND_EXCEPTION);
        });
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        var userId = request.getUserID();
        userRepository.findById(userId).ifPresentOrElse(userJPA -> {
            boolean oldPasswordIsCorrect = passwordEncoder.matches(request.getOldPassword(), userJPA.getPassword());
            if(!oldPasswordIsCorrect)
                throw new SocialNetworkException(SocialNetworkErrors.OLD_PASSWORD_INCORRECT);

            userJPA.setPassword(request.getNewPassword());
            userRepository.save(userJPA);
        }, () -> {
            throw new SocialNetworkException(SocialNetworkErrors.USER_NOT_FOUND_EXCEPTION);
        });
    }

    @Override
    public User authorizeRole(RoleRequest request) {
        var userJPA = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new SocialNetworkException(SocialNetworkErrors.USER_NOT_FOUND_EXCEPTION));

        var roleJPAS = request.getRoles()
                .stream()
                .map(role ->
                    roleRepository.findByName(role)
                            .orElseThrow(() ->
                                new SocialNetworkException(SocialNetworkErrors.ROLE_DOESNT_EXIST, String.format("Role %s doesn't exist", role))
                            )
                )
                .collect(Collectors.toList());

        userJPA.setRoles(roleJPAS);
        var savedUserJPA = userRepository.save(userJPA);
        return mapper.to(savedUserJPA);
    }
}
