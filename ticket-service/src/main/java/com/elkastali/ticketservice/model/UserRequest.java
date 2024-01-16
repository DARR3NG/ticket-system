package com.elkastali.ticketservice.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
@SuperBuilder
public class UserRequest {


    private String nom;
    private String prenom;
    private String email;
    private String photo;
    private String tel;
    private String roleName;
    private Boolean isActive;
}
