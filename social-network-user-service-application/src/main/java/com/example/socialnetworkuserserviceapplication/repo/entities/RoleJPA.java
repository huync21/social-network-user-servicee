package com.example.socialnetworkuserserviceapplication.repo.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "Role")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
}
