package com.elkastali.ticketservice.repository;

import com.elkastali.ticketservice.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByClientId(Long clientId);
    List<Ticket> findByTechId(Long techId);
    List<Ticket> findByStatus(String status);
    List<Ticket> findByPriority(String priority);
    @Query("SELECT t.techId as techId, COUNT(t) as ticketsResolved FROM Ticket t WHERE t.status = 'RESOLVED' GROUP BY t.techId")
    List<Map<String, Object>> getTicketsResolvedByTech();

    List<Ticket> findByTitre(String titre);
    List<Ticket> findByTitreContainsIgnoreCase(String titre);

    List<Ticket> findTicketByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT t FROM Ticket t WHERE t.resolvedAt IS NOT NULL ORDER BY t.resolvedAt DESC limit 6")
    List<Ticket> findRecentlyResolvedTickets();
    @Query("SELECT t FROM Ticket t ORDER BY t.createdAt DESC limit 4")
    List<Ticket> findRecentlyAddedTickets();



    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.status = 'InProgress'")
    Long countInProgressTickets();

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.status = 'Resolved'")
    Long countResolvedTickets();

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.status = 'Closed'")
    Long countClosedTickets();

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.status = 'Cancelled'")
    Long countCancelledTickets();

    long countByTechIdAndStatus(Long techId, String status);

    long countByTechIdAndStatusNot(Long techId, String status);
    @Query("SELECT COUNT(t) FROM Ticket t")
    Long countTicket();



    @Query("SELECT t.clientId as clientId, COUNT(t) as tickets FROM Ticket t GROUP BY t.clientId")

    List<Map<String, Object>> countByClientId();
}
