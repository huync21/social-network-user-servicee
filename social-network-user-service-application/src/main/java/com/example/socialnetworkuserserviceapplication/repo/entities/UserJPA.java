package com.example.socialnetworkuserserviceapplication.repo.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(schema = "user")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class UserJPA implements UserDetails {
//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Type(type="uuid-char")
//    UUID uuid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    static final long serialVersionUID = 1L;
    String email;
    String username;
    String password;
    String avatar;
    String description;
    boolean enabled;

    @Column(name = "account_locked")
    boolean accountNonLocked;

    @Column(name = "account_expired")
    boolean accountNonExpired;

    @Column(name = "credentials_expired")
    boolean credentialsNonExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "role_user", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private List<RoleJPA> roles;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountNonExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountNonLocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        roles.forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        });

        return authorities;
    }

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
