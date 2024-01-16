package com.elkastali.ticketservice.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
public class Role {


    private Long id;
    private String name;
    private String description;
}
