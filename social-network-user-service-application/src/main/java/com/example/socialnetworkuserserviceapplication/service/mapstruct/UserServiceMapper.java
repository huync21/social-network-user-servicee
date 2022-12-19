package com.example.socialnetworkuserserviceapplication.service.mapstruct;

import com.example.socialnetworkuserserviceapplication.repo.entities.UserJPA;
import com.example.socialnetworkuserserviceapplication.service.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserServiceMapper {
    UserJPA from(User user);
    User to(UserJPA userJPA);
    List<User> to(List<UserJPA> userJPAS);

    UserJPA merge(@MappingTarget UserJPA target, User user);
}
