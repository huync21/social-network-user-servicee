package com.example.socialnetworkuserserviceapplication.repo;

import com.example.socialnetworkuserserviceapplication.repo.entities.UsernameChangeLogJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsernameChangeLogRepository extends JpaRepository<UsernameChangeLogJPA, Integer> {
}
