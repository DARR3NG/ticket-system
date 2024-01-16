package com.elkastali.ticketservice.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest
                                                                          request) {
                        CorsConfiguration config = new CorsConfiguration();

                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }).and()



                .authorizeHttpRequests()
                .requestMatchers("/swagger-ui-custom.html").permitAll()
                .requestMatchers("/tickets/**").permitAll()
                .requestMatchers("/tickets/findrecentlyresolvedtickets/**")
                .hasAnyAuthority("ADMIN")
                .requestMatchers("/tickets/countbyclientid/**")
                .hasAnyAuthority("ADMIN")
                .requestMatchers("/tickets/findbyclient/**")
                .permitAll()
                .requestMatchers("/tickets/createbyclient/**")
                .permitAll()
            .requestMatchers("/tickets/assigntotech/**")
            .permitAll()
                //consulter un produit par son id

//ajouter un nouveau produit
                //.requestMatchers(HttpMethod.POST,"/tickets/**").hasAnyAuthority("ADMIN","CLIENT","TECHNICIEN")
//modifier un produit
                //.requestMatchers(HttpMethod.PUT,"/api/updateprod/**").hasAuthority("ADMIN")
//supprimer un produit
                //.requestMatchers(HttpMethod.DELETE,"/api/delprod/**").hasAuthority("ADMIN")
                .anyRequest().authenticated().and()
                .addFilterBefore(new
                        JWTAuthorizationFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }
}

