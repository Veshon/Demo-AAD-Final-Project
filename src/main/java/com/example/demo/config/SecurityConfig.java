/*
package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${secure.username}")
    private String userName;
    @Value("${secure.password}")
    private String password;
    @Value("${secure.role}")
    private String role;

    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails principleUser = User.withDefaultPasswordEncoder()
                .username(userName)
                .password(password)
                .roles(role).build();
        return new InMemoryUserDetailsManager(principleUser);
    }
}

*/
/*@EnableWebSecurity
public class SecurityConfig {

    @Value("${secure.username}")
    private String userName;
    @Value("${secure.password}")
    private String password;
    @Value("${secure.role}")
    private String role;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors() // Enable CORS configuration
                .and()
                .csrf().disable() // Disable CSRF if necessary
                .authorizeRequests()
                .anyRequest().permitAll(); // Adjust authorization as needed

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails principleUser = User.withDefaultPasswordEncoder()
                .username(userName)
                .password(password)
                .roles(role).build();
        return new InMemoryUserDetailsManager(principleUser);
    }
}*//*



*/
