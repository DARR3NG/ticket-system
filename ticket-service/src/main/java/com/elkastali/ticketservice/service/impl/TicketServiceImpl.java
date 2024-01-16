package com.elkastali.ticketservice.service.impl;

import com.elkastali.ticketservice.entities.Ticket;
import com.elkastali.ticketservice.entities.User;
import com.elkastali.ticketservice.model.TicketResposne;
import com.elkastali.ticketservice.repository.TicketRepository;
import com.elkastali.ticketservice.service.EmailService;
import com.elkastali.ticketservice.service.TicketService;
import com.elkastali.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final EmailService emailService;
    private final UserService userService;

    @Override
    public Ticket createTicket(Ticket ticket){
        System.out.println(ticket.getClientId()+" "+ticket.getTechId());
        User client = userService.findUserById(ticket.getClientId() );

        User tech = userService.findUserById(ticket.getTechId());


        emailService.sendMail(client,"Your ticket has been created");
        if(ticket.getTechId()!=null)
        emailService.sendMail(tech,"You have been assigned to a ticket");

        System.err.println(ticket.getPriority());
        ticket.setCreatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    @Override
    public TicketResposne getTicket(Long id) {
        Ticket ticket= ticketRepository.findById(id).orElse(null);
        return mapTicketToTicketResponse(ticket);
    }

    @Override
    public List<TicketResposne> getAllTickets() {
        List<TicketResposne> ticketResposnes= ticketRepository.findAll().stream().map(ticket -> mapTicketToTicketResponse(ticket)).collect(Collectors.toList());
        return ticketResposnes;

    }

    @Override
    public Ticket updateTicket(Long id, Ticket ticket) {
        Ticket ticketToSave= ticketRepository.findById(id).orElse(null);
        ticketToSave.setId(id);
        ticketToSave.setTitre(ticket.getTitre());
        ticketToSave.setDescription(ticket.getDescription());
        ticketToSave.setClientId(ticket.getClientId());
        ticketToSave.setPriority(ticket.getPriority());

        ticketToSave.setTechId(ticket.getTechId());
        ticketToSave.setStatus(ticket.getStatus());

        if(ticketToSave.getStatus().equals("Resolved")){
            ticketToSave.setResolvedAt(LocalDateTime.now());
        }

        senEmail(userService.findUserById(ticket.getClientId()),ticketToSave.getStatus());
        return ticketRepository.save(ticketToSave);
    }

    @Override
    public Boolean deleteTicket(Long id) {
try {
    ticketRepository.deleteById(id);
    return  true;
}catch (Exception e){
    return false;
}
    }

    @Override
    public List<TicketResposne> findByClientId(Long clientId) {

        List<TicketResposne> tickets = ticketRepository.findByClientId(clientId).stream().map(ticket -> mapTicketToTicketResponse(ticket)).collect(Collectors.toList());;
        return tickets;
    }



    @Override
    public List<TicketResposne> findByTechId(Long techId) {
        List<TicketResposne> tickets = ticketRepository.findByTechId(techId).stream().map(ticket -> mapTicketToTicketResponse(ticket)).collect(Collectors.toList());;;
        return tickets;

    }

    @Override
    public List<TicketResposne> findByStatus(String status) {
        List<TicketResposne> tickets = ticketRepository.findByStatus(status).stream().map(ticket -> mapTicketToTicketResponse(ticket)).collect(Collectors.toList());;;
        return tickets;
    }

    @Override
    public List<TicketResposne> findByPriority(String priority) {
        List<TicketResposne> tickets = ticketRepository.findByPriority(priority).stream().map(ticket -> mapTicketToTicketResponse(ticket)).collect(Collectors.toList());;;

        return tickets;
    }

    @Override
    public List<TicketResposne> findByTitre(String titre) {
        List<TicketResposne> tickets = ticketRepository.findByTitre(titre).stream().map(ticket -> mapTicketToTicketResponse(ticket)).collect(Collectors.toList());;;
        return tickets;
    }

    @Override
    public List<TicketResposne> findByTitreContaining(String titre) {
        List<TicketResposne> tickets = ticketRepository.findByTitreContainsIgnoreCase(titre).stream().map(ticket -> mapTicketToTicketResponse(ticket)).collect(Collectors.toList());;;
        return tickets;
    }



    @Override
    public List<TicketResposne> findTicketByCreatedAtBetween(LocalDateTime from, LocalDateTime to) {
        List<TicketResposne> tickets = ticketRepository.findTicketByCreatedAtBetween(from,to).stream().map(ticket -> mapTicketToTicketResponse(ticket)).collect(Collectors.toList());;;
        return null;
    }

    @Override
    public Ticket assignTicket(Long ticketId, Long techId) {

        return null;
    }


    @Override
    public Ticket closeTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if(ticket != null){
            ticket.setStatus("Closed");
            ticketRepository.save(ticket);
            User client = userService.findUserById(ticket.getClientId());
            emailService.sendMail(client,"Your ticket has been closed");
            return ticket;
        }
        return ticket;
    }

    @Override
    public Ticket cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if(ticket != null){
            ticket.setStatus("Cancelled");
            ticketRepository.save(ticket);
            User client = userService.findUserById(ticket.getClientId());
            emailService.sendMail(client,"Your ticket has been canceled");
            return ticket;
        }
        return null;
    }

    @Override
    public Ticket resolveTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if(ticket != null){
            ticket.setStatus("Resolved");
            ticketRepository.save(ticket);
            User client = userService.findUserById(ticket.getClientId());
            emailService.sendMail(client,"Your ticket has been resolved");
            return ticket;
        }
        return null;
    }



    @Override
    public Ticket InProgressTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if(ticket != null){
            ticket.setStatus("InProgress");
            ticketRepository.save(ticket);
            User client = userService.findUserById(ticket.getClientId());
            emailService.sendMail(client,"Your ticket is in progress");
            return ticket;
        }
        return null;
    }

    @Override
    public List<Ticket> findRecentlyResolvedTickets() {
        return ticketRepository.findRecentlyResolvedTickets();
    }

    @Override
    public List<Ticket> findRecentlyAddedTickets() {
        return ticketRepository.findRecentlyAddedTickets();
    }

    @Override
    public Long countInProgressTickets() {
        return ticketRepository.countInProgressTickets();
    }

    @Override
    public Long countResolvedTickets() {
        return ticketRepository.countResolvedTickets();
    }

    @Override
    public Long countClosedTickets() {
      return ticketRepository.countClosedTickets();
    }

    @Override
    public Long countCancelledTickets() {
        return ticketRepository.countCancelledTickets();
    }

    @Override
    public Long countTicket() {
        return ticketRepository.countTicket();
    }

    @Override
    public List<Map<String, Object>> countByClientId() {
        List<Map<String, Object>> tickets= ticketRepository.countByClientId();

        // Récupérer une map de tous les utilisateurs par leur ID
        Map<Long, User> usersById = userService.findAllUsers().stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        // Transformer la liste de tickets en une nouvelle liste avec le nom du client comme clé
        List<Map<String, Object>> ticketsWithNames = tickets.stream()
                .map(ticket -> {
                    Long clientId = (Long) ticket.get("clientId");
                    User client = usersById.get(clientId);

                    // Créer une nouvelle Map avec le nom du client comme clé
                    Map<String, Object> ticketWithClientName = new HashMap<>(ticket);
                    ticketWithClientName.put("clientName", client != null ? client.getNom() : null);

                    return ticketWithClientName;
                })
                .collect(Collectors.toList());

        return ticketsWithNames;
    }

    @Override
    public Boolean assignTicketToTech(Long ticketId, Long techId) {

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if(ticket != null){
            ticket.setTechId(techId);
            ticket.setStatus("Assigned");
            ticketRepository.save(ticket);
            User client = userService.findUserById(ticket.getClientId());
            User tech = userService.findUserById(ticket.getTechId());
            emailService.sendMail(client,"Your ticket has been assigned to "+tech.getNom());
            emailService.sendMail(tech,"You have been assigned to a ticket");
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> getTicketsResolvedByTech() {
        List<Map<String, Object>> tickets = ticketRepository.getTicketsResolvedByTech();

        // Récupérer une map de tous les utilisateurs par leur ID
        Map<Long, User> usersById = userService.findAllUsers().stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        // Transformer la liste de tickets en une nouvelle liste avec le nom du client comme clé
        List<Map<String, Object>> ticketsWithNames = tickets.stream()
                .map(ticket -> {
                    Long techId = (Long) ticket.get("techId");
                    User tech = usersById.get(techId);

                    // Créer une nouvelle Map avec le nom du client comme clé
                    Map<String, Object> ticketWithClientName = new HashMap<>(ticket);
                    ticketWithClientName.put("techName", tech != null ? tech.getNom() : null);

                    return ticketWithClientName;
                })
                .collect(Collectors.toList());

        return ticketsWithNames;
    }


    private TicketResposne mapTicketToTicketResponse(Ticket ticket) {
        TicketResposne ticketResposne = new TicketResposne();
            ticketResposne.setId(ticket.getId());
            ticketResposne.setClientId(ticket.getClientId());
            ticketResposne.setTechId(ticket.getTechId());
            ticketResposne.setTitre(ticket.getTitre());
            ticketResposne.setDescription(ticket.getDescription());
            ticketResposne.setCreatedAt(ticket.getCreatedAt());
            ticketResposne.setStatus(ticket.getStatus());

            ticketResposne.setPriority(ticket.getPriority());
            ticketResposne.setClient(userService.findUserById(ticket.getClientId()));
        System.out.println("IDDD NUKLL"+ticket.getTechId());
        if(ticket.getTechId()!=null)
            ticketResposne.setTechnicien(userService.findUserById(ticket.getTechId()));

        return ticketResposne;
    }

    @Override
    public Ticket createTicketByClient(Ticket ticket){
        System.out.println(ticket.getClientId()+" "+ticket.getTechId());
        User client = userService.findUserById(ticket.getClientId() );
        //User tech = userService.findUserById(ticket.getTechId());


        emailService.sendMail(client,"Your ticket has been created");



        System.err.println(ticket.getPriority());
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setStatus("Created");
        return ticketRepository.save(ticket);
    }





    private void  senEmail(User user,String status){
        emailService.sendMail(user,"Your ticket has been "+status);
    }
}
