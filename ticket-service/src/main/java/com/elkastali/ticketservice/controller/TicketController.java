package com.elkastali.ticketservice.controller;

import com.elkastali.ticketservice.entities.Ticket;
import com.elkastali.ticketservice.model.HttpResponse;
import com.elkastali.ticketservice.model.TicketResposne;
import com.elkastali.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;



    @PostMapping()
    public ResponseEntity<HttpResponse> createTicket(@RequestBody Ticket ticket) {

        System.err.println("given ids"+ticket.getClientId()+" "+ticket.getTechId());
        ticketService.createTicket(ticket);

          return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ticket", ticket))
                        .message("Technicien created successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );

    }


    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getTicket(@PathVariable Long id) {
        TicketResposne ticket = ticketService.getTicket(id);
        if(ticket == null){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("ticket", ticket))
                            .message("Ticket not found")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ticket", ticket))
                        .message("Ticket found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getAllTickets() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.getAllTickets()))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        System.out.println("update null id"+id+" "+ticket.getTechId()+" "+ticket.getClientId());
        Ticket ticketToUpdate = ticketService.updateTicket(id, ticket);
        if(ticketToUpdate == null){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("ticket", ticket))
                            .message("Ticket not found")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ticket", ticketToUpdate))
                        .message("Ticket updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }



    @GetMapping("/findbyclient/{clientId}")
    public ResponseEntity<HttpResponse> getAllTicketsByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.findByClientId(clientId)))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/findbytech/{techId}")
    public ResponseEntity<HttpResponse> getAllTicketsByTech(@PathVariable Long techId) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.findByTechId(techId)))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteTicket(@PathVariable Long id) {
        Boolean deleted=ticketService.deleteTicket(id);
        if(!deleted ){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())

                            .message("Ticket not found")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())

                        .message("Ticket deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
    @GetMapping("/findbystatus/{status}")
    public ResponseEntity<HttpResponse> getAllTicketsByStatus(@PathVariable String status) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.findByStatus(status)))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @GetMapping("/findbytitre/{titre}")
    public ResponseEntity<HttpResponse> getAllTicketsByTitre(@PathVariable String titre) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.findByTitre(titre)))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/findbytitrecontaining/{titre}")
    public ResponseEntity<HttpResponse> getAllTicketsByTitreContaining(@PathVariable String titre) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.findByTitreContaining(titre)))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/findbycreatedatbetween/{from}/{to}")
    public ResponseEntity<HttpResponse> getAllTicketsByCreatedAtBetween(@PathVariable LocalDateTime from, @PathVariable LocalDateTime to) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.findTicketByCreatedAtBetween(from,to)))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @PutMapping("/assign/{ticketId}/{techId}")
    public ResponseEntity<HttpResponse> assignTicket(@PathVariable Long ticketId, @PathVariable Long techId) {
        Ticket ticket = ticketService.assignTicket(ticketId, techId);
        if(ticket == null){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("ticket", ticket))
                            .message("Ticket not found")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ticket", ticket))
                        .message("Ticket assigned")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @PutMapping("/close/{ticketId}")
    public ResponseEntity<HttpResponse> closeTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.closeTicket(ticketId);
        if(ticket == null){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("ticket", ticket))
                            .message("Ticket not found")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ticket", ticket))
                        .message("Ticket closed")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @PutMapping("/cancel/{ticketId}")
    public ResponseEntity<HttpResponse> cancelTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.cancelTicket(ticketId);
        if(ticket == null){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("ticket", ticket))
                            .message("Ticket not found")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ticket", ticket))
                        .message("Ticket canceled")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @PutMapping("/resolve/{ticketId}")
    public ResponseEntity<HttpResponse> resolveTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.resolveTicket(ticketId);
        if(ticket == null){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("ticket", ticket))
                            .message("Ticket not found")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ticket", ticket))
                        .message("Ticket resolved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/findbypriority/{priority}")
    public ResponseEntity<HttpResponse> getAllTicketsByPriority(@PathVariable String priority) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.findByPriority(priority)))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @PutMapping("/inprogress/{ticketId}")
    public ResponseEntity<HttpResponse> inProgressTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.InProgressTicket(ticketId);
        if(ticket == null){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("ticket", ticket))
                            .message("Ticket not found")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ticket", ticket))
                        .message("Ticket in progress")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @GetMapping("/getticketsresolvedbytech")
    public ResponseEntity<HttpResponse> getTicketsResolvedByTech() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.getTicketsResolvedByTech()))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


@GetMapping("/findrecentlyresolvedtickets")
    public ResponseEntity<HttpResponse> findRecentlyResolvedTickets() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.findRecentlyResolvedTickets()))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @GetMapping("/findrecentlyaddedtickets")
    public ResponseEntity<HttpResponse> findRecentlyAddedTickets() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("tickets", ticketService.findRecentlyAddedTickets()))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }



    @GetMapping("/countinprogresstickets")
    public ResponseEntity<HttpResponse> countInProgressTickets() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("count", ticketService.countInProgressTickets()))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/countresolvedtickets")
    public ResponseEntity<HttpResponse> countResolvedTickets() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("count", ticketService.countResolvedTickets()))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @GetMapping("/countclosedtickets")
public ResponseEntity<HttpResponse> countClosedTickets() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("count", ticketService.countClosedTickets()))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @GetMapping("/countcancelledtickets")
    public ResponseEntity<HttpResponse> countCancelledTickets() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("count", ticketService.countCancelledTickets()))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/countticket")
    public ResponseEntity<HttpResponse> countTicket() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("count", ticketService.countTicket()))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @GetMapping("/countbyclientid")
    public ResponseEntity<HttpResponse> countByClientId() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("count", ticketService.countByClientId()))
                        .message("Tickets found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }



    @PostMapping("/createbyclient")
    public ResponseEntity<HttpResponse> createTicketByClient(@RequestBody Ticket ticket) {

        System.err.println("given ids"+ticket.getClientId()+" "+ticket.getTechId());
        ticketService.createTicketByClient(ticket);

        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ticket", ticket))
                        .message("Ticket created successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );

    }

    @PutMapping("/assigntotech/{ticketId}/{techId}")
    public ResponseEntity<HttpResponse> assignTicketToTech(@PathVariable Long ticketId, @PathVariable Long techId) {
        Boolean assigned=ticketService.assignTicketToTech(ticketId, techId);
        if(!assigned ){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())

                            .message("Ticket not found")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())

                        .message("Ticket assigned")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


}
