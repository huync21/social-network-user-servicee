package com.example.socialnetworkuserserviceapplication.sercurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
public class SocialNetworkSecurityConfigurerAdapter {
    @Autowired
    private SecurityConfigurationProperties securityConfigurationProperties;
    @Autowired
    private SocialNetworkBasicAuthenticationEntryPoint authenticationEntryPoint;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        var basicAuths = securityConfigurationProperties.getBasicAuths();
        for (SecurityConfigurationProperties.BasicAuth basicAuth : basicAuths) {
            List<GrantedAuthority> authorities = basicAuth.getRoles()
                    .stream().map(authority -> new SimpleGrantedAuthority(authority))
                    .collect(Collectors.toList());
            auth
                    .inMemoryAuthentication()
                    .withUser(basicAuth.getUsername())
                    .password(new BCryptPasswordEncoder().encode(basicAuth.getPassword()))
                    .authorities(authorities);

        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                    .antMatchers("/api/**")
                    .and()
                .authorizeRequests()
                    .antMatchers("/users/**")
                    .hasAnyRole("admin", "user")
                    .anyRequest().authenticated()
                    .and()
                .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }


}
