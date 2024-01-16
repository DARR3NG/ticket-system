package org.elkastali.usericroservice;

import org.elkastali.usericroservice.entities.User;
import org.elkastali.usericroservice.model.UserRequest;
import org.elkastali.usericroservice.repository.UserRepository;
import org.elkastali.usericroservice.service.UserService;
import org.elkastali.usericroservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@SpringBootApplication


public class UserIcroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserIcroserviceApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder getBCE() {
        return new BCryptPasswordEncoder();
    }



   /* @Bean
    CommandLineRunner commandLineRunner( UserService userService) {
        return args -> {
            List.of(UserRequest.builder()
                            .nom("Elkastali")
                            .prenom("ADMIN")
                            .email("elkastali.otmane05@gmail.com")
                            .tel("0627498914")
                            .isActive(true)

                            .roleName("ADMIN")
                                            .build(),
                    UserRequest.builder()
                            .nom("Elkastali")
                            .prenom("CLIENT")
                            .email("elkastaliotmane@gmail.com")
                            .tel("0627498914")
                            .isActive(true)
                            .username("elkastali2")
                            .roleName("CLIENT")
                            .build(),
                    UserRequest.builder()
                            .nom("Elkastali")
                            .prenom("TECHNICIEN")
                            .email("elkastaliO@gmail.com")
                            .tel("0627498914")
                            .roleName("TECHNICIEN")
                            .username("elkastali3")
                            .isActive(true)
                            .build()


                    ).forEach(user -> userService.saveUser(user));

        };
    }*/



}
