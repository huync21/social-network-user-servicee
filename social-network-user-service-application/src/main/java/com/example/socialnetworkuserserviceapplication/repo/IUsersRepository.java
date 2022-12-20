package com.example.socialnetworkuserserviceapplication.repo;

import com.example.socialnetworkuserserviceapplication.repo.entities.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<UserJPA, Integer> {
    List<UserJPA> findByUsernameContaining(String keyword);
    Optional<UserJPA> findByUsername(String username);
}
