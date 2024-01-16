package com.elkastali.ticketservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
public class User {

    private Long id;




    private String nom;

    private String prenom;


    private String email;
    private String password;

    private String photo;



    private String tel;
    private Boolean isAdmin;

    private LocalDateTime createdAt;

    private LocalDateTime last_Login;

    private Boolean isStaff;


    private Boolean isActive;


    private List<Role> roles=new ArrayList<>();
}
