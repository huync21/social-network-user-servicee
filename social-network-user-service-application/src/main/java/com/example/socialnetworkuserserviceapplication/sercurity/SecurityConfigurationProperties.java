package com.example.socialnetworkuserserviceapplication.sercurity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "socialnetwork.security.client")
public class SecurityConfigurationProperties {
    List<BasicAuth> basicAuths;
    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class BasicAuth {
        String username;
        String password;
        List<String> roles;
    }
}
