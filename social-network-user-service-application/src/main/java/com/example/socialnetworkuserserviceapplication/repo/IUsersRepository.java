package com.example.socialnetworkuserserviceapplication.repo;

import com.example.socialnetworkuserserviceapplication.repo.entities.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUsersRepository extends JpaRepository<UserJPA, UUID> {
    List<UserJPA> findByUsernameContainingAndEnabled(String keyword, boolean enable);

    Optional<UserJPA> findByUsername(String username);
}
