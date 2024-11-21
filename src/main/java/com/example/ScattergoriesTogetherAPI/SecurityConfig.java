package com.example.ScattergoriesTogetherAPI;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.config.Customizer;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        // load two default users
        inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        inMemoryUserDetailsManager.createUser(User.withUsername("user").password(passwordEncoder().encode("user"))
                .roles("USER").build());

        inMemoryUserDetailsManager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin"))
                .roles("ADMIN").build());

        // load all users from db
//        ArrayList<com.example.Models.User> users = UserRestSQL.getAll();
//        for (com.example.Models.User u : users) {
//            inMemoryUserDetailsManager
//                    .createUser(User.withUsername(u.getName()).password(passwordEncoder().encode(u.getPassword()))
//                            .roles(u.getRole()).build());
//        }

        return inMemoryUserDetailsManager;
    }

    // for Dev mode, everything is open mode, security filter chain:
    // this is pretty much the Mod03 configuration, but opening everything up for dev/test purposes
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET).permitAll()
                .requestMatchers(HttpMethod.POST).permitAll()
                .requestMatchers(HttpMethod.PUT, "/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/*/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/*/{id}").permitAll()
                .anyRequest().authenticated()).httpBasic(Customizer.withDefaults());

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOriginPatterns(List.of("*"));
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
            config.setAllowedHeaders(List.of("*"));
            config.setExposedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
            config.setAllowCredentials(true); // if using auth headers, set to true
            return config;
        }));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
