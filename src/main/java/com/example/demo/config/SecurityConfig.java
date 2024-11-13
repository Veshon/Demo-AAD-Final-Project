package com.example.demo.config;

import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final JWTConfigFilter jwtConfigFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS with custom configuration
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/fields/**").hasAnyAuthority("ROLE_SCIENTIST", "ROLE_MANAGER") // Only scientists and managers can POST
                                .requestMatchers(HttpMethod.GET, "/api/v1/fields/**").hasAnyAuthority("ROLE_SCIENTIST", "ROLE_MANAGER") // Only scientists and managers can POST
                                .requestMatchers(HttpMethod.PUT, "/api/v1/fields/**").hasAnyAuthority("ROLE_SCIENTIST", "ROLE_MANAGER") // Only scientists and managers can POST
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/fields/**").hasAnyAuthority("ROLE_SCIENTIST", "ROLE_MANAGER") // Only scientists and managers can POST

                                .requestMatchers(HttpMethod.POST, "/api/v1/crops/**").hasAuthority("ROLE_MANAGER") // Only managers can perform POST
                                .requestMatchers(HttpMethod.GET, "/api/v1/crops/**").hasAuthority("ROLE_MANAGER") // Only managers can perform GET
                                .requestMatchers(HttpMethod.PUT, "/api/v1/crops/**").hasAuthority("ROLE_MANAGER") // Only managers can perform PUT
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/crops/**").hasAuthority("ROLE_MANAGER") // Only managers can perform DELETE

                                .requestMatchers(HttpMethod.POST, "/api/v1/equipments/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST
                                .requestMatchers(HttpMethod.GET, "/api/v1/equipments/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST
                                .requestMatchers(HttpMethod.PUT, "/api/v1/equipments/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/equipments/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST


                                .requestMatchers(HttpMethod.POST, "/api/v1/logs/**").hasAuthority("ROLE_MANAGER") // Only managers can perform POST
                                .requestMatchers(HttpMethod.GET, "/api/v1/logs/**").hasAuthority("ROLE_MANAGER") // Only managers can perform GET
                                .requestMatchers(HttpMethod.PUT, "/api/v1/logs/**").hasAuthority("ROLE_MANAGER") // Only managers can perform PUT
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/logs/**").hasAuthority("ROLE_MANAGER") // Only managers can perform DELETE

                                .requestMatchers(HttpMethod.POST, "/api/v1/staff/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST
                                .requestMatchers(HttpMethod.GET, "/api/v1/staff/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST
                                .requestMatchers(HttpMethod.PUT, "/api/v1/staff/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/staff/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST


                                .requestMatchers(HttpMethod.POST, "/api/v1/vehicles/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST
                                .requestMatchers(HttpMethod.GET, "/api/v1/vehicles/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST
                                .requestMatchers(HttpMethod.PUT, "/api/v1/vehicles/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/vehicles/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER") // Only admins and managers can POST

/*                                .requestMatchers(HttpMethod.POST, "/api/v1/fields/**").hasAuthority("ROLE_ADMIN") // Only admins can perform POST
                                .requestMatchers("/api/v1/fields/**")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/fields/**").authenticated() // Allow GET requests to be accessed by authenticated users*/
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtConfigFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS configuration bean
    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:63342")); // Add allowed origins
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed methods
        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Allowed headers
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(userService.userDetailsService());
        dap.setPasswordEncoder(passwordEncoder());
        return dap;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
