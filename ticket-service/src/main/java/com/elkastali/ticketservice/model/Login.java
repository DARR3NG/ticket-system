package com.elkastali.ticketservice.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class Login {

    private String email;
    private String password;
}
