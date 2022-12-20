package com.example.socialnetworkuserserviceapplication.sercurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties({SecurityConfigurationProperties.class, SecurityEndpointAuthorizationProperties.class})
public class SocialNetworkSecurityConfigurerAdapter {
    @Autowired
    private SecurityConfigurationProperties securityConfigurationProperties;
    @Autowired
    private SecurityEndpointAuthorizationProperties securityEndpointAuthorizationProperties;
    @Autowired
    private SocialNetworkBasicAuthenticationEntryPoint authenticationEntryPoint;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        var basicAuths = securityConfigurationProperties.getBasicAuths();
//        for (SecurityConfigurationProperties.BasicAuth basicAuth : basicAuths) {
//            String[] authorities = basicAuth.getRoles().stream().toArray(String[]::new);
//            auth
//                    .inMemoryAuthentication()
//                    .withUser(basicAuth.getUsername())
//                    .password(new BCryptPasswordEncoder().encode(basicAuth.getPassword()))
//                    .roles(authorities);
//
//        }
//    }

    @Bean
    public UserDetailsService users() {
        var basicAuths = securityConfigurationProperties.getBasicAuths();
        List<UserDetails> userDetails = new ArrayList<>();
        for (SecurityConfigurationProperties.BasicAuth basicAuth : basicAuths) {
            String[] authorities = basicAuth.getRoles().stream().toArray(String[]::new);
            UserDetails user = User.builder()
                    .username(basicAuth.getUsername())
                    .password(new BCryptPasswordEncoder().encode(basicAuth.getPassword()))
                    .roles(authorities)
                    .build();
            userDetails.add(user);
        }

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        List<SecurityEndpointAuthorizationProperties.Authorization> endpointAuthorizations = securityEndpointAuthorizationProperties.getEndpointAuthorizations();
        endpointAuthorizations.forEach(authorization -> {
            try {
                http
                        .authorizeRequests()
                            .antMatchers(authorization.getUrlPattern())
                            .hasAnyRole(authorization.getRoles().stream().toArray(String[]::new));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        http
                .requestMatchers()
                    .antMatchers("/api/**")
                    .and()
                .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .cors().and().csrf().disable();
        return http.build();
    }


}
