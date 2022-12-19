package com.example.socialnetworkuserserviceapplication.repo;

import com.example.socialnetworkuserserviceapplication.repo.entities.RoleJPA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleJPA, Integer> {
    Optional<RoleJPA> findByName(String role);
}
