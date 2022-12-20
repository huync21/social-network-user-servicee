package com.example.socialnetworkuserserviceapplication.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "socialnetwork.security")
public class SecurityEndpointAuthorizationProperties {
    List<Authorization> endpointAuthorizations;
    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Authorization {
        String urlPattern;
        List<String> roles;
    }
}
