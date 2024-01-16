package org.elkastali.usericroservice.security;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Autowired
   private  AuthenticationManager authMgr;


    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             BCryptPasswordEncoder bCryptPasswordEncoder,
                                             UserDetailsService userDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                   .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest
                                                                          request) {
                        CorsConfiguration config = new CorsConfiguration();

                        config.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://localhost:8090"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }).and()
                .authorizeHttpRequests()
                .requestMatchers("/api/users/verify**").permitAll()
                .requestMatchers("/swagger-ui-custom.html").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("swagger-ui/**").permitAll()
                .requestMatchers("/api/users/**")
                .permitAll()
                .requestMatchers("/api/users/switchstatus/*")
                .hasAnyAuthority("ADMIN")
                .requestMatchers("/api/users/findbyemail/**")
                .hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new
                        JWTAuthenticationFilter(authMgr), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter (authMgr),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new
                        JWTAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
        return http.build();


    }

}
