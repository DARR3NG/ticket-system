package com.elkastali.ticketservice.model;

import com.elkastali.ticketservice.entities.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class TicketResposne {


    private Long id;
    private String titre;
    private String description;

    private LocalDateTime createdAt;


    private String status;
    private String priority;

    private Long clientId;
    private Long techId;

    private User client;
    private User technicien;

}
