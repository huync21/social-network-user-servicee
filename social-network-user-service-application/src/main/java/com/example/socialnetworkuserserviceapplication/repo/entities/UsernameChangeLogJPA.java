package com.example.socialnetworkuserserviceapplication.repo.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "user_name_change_log")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UsernameChangeLogJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "old_user_name")
    String oldUserName;
    @Column(name = "new_user_name")
    String newUserName;
    @Column(name = "user_id")
    Integer userID;
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    long createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    long modifiedDate;
    @Column(name = "created_by")
    @CreatedBy
    String createdBy;
    @Column(name = "modified_by")
    @LastModifiedBy
    String modifiedBy;
}
