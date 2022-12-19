package com.example.socialnetworkuserserviceapplication.service;

import com.example.socialnetworkuserserviceapplication.repo.entities.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserJPA, Integer> {
    List<UserJPA> findByUsernameContainingAndEnabled(String keyword, boolean enable);
}
