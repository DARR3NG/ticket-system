package com.elkastali.ticketservice.service;

import com.elkastali.ticketservice.entities.Ticket;
import com.elkastali.ticketservice.model.TicketResposne;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TicketService {
    public List<Map<String, Object>> getTicketsResolvedByTech();
    Ticket createTicket(Ticket ticket);

    Ticket createTicketByClient(Ticket ticket);
    TicketResposne getTicket(Long id);
    List<TicketResposne> getAllTickets();
    Ticket updateTicket(Long id,Ticket ticket);
    Boolean deleteTicket(Long id);
    List<TicketResposne> findByClientId(Long clientId);
    List<TicketResposne> findByTechId(Long techId);
    List<TicketResposne> findByStatus(String status);
    List<TicketResposne> findByPriority(String priority);
    List<TicketResposne> findByTitre(String titre);
    List<TicketResposne> findByTitreContaining(String titre);


    List<TicketResposne> findTicketByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    Ticket assignTicket(Long ticketId, Long techId);
    Ticket closeTicket(Long ticketId);
    Ticket cancelTicket(Long ticketId);
    Ticket resolveTicket(Long ticketId);

    Ticket InProgressTicket(Long ticketId);


    List<Ticket> findRecentlyResolvedTickets();

    List<Ticket> findRecentlyAddedTickets();

    Long countInProgressTickets();
    Long countResolvedTickets();
    Long countClosedTickets();
    Long countCancelledTickets();

    Long countTicket();

    List<Map<String, Object>> countByClientId();

    Boolean assignTicketToTech(Long ticketId, Long techId);


}
