package org.elkastali.usericroservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.elkastali.usericroservice.entities.User;
import org.elkastali.usericroservice.model.HttpResponse;
import org.elkastali.usericroservice.model.UserRequest;
import org.elkastali.usericroservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;




    @PostMapping("/register")
    public ResponseEntity<HttpResponse> registerUser(@RequestBody UserRequest userRequest) {
        try{
            User newUser = userService.saveUser(userRequest);

            return ResponseEntity.created(URI.create("")).body(
                   HttpResponse.builder()
                           .timeStamp(LocalDateTime.now().toString())
                           .data(Map.of("user", newUser))
                           .message("User created successfully")
                           .status(HttpStatus.CREATED)
                           .statusCode(HttpStatus.CREATED.value())
                           .build()
           );

       }catch (RuntimeException e){
           return ResponseEntity.badRequest().body(
                   HttpResponse.builder()
                           .timeStamp(LocalDateTime.now().toString())
                           .data(Map.of("user", userRequest))
                           .message(e.getMessage())
                           .status(HttpStatus.BAD_REQUEST)
                           .statusCode(HttpStatus.BAD_REQUEST.value())
                           .build()
           );}
    }

    @PostMapping("/client")
    public ResponseEntity<HttpResponse> addClient(@Valid @RequestBody  UserRequest userRequest) {

        try {

            userRequest.setRoleName("CLIENT");
            User newUser = userService.saveUser(userRequest);

            return ResponseEntity.created(URI.create("")).body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("user", newUser))
                            .message("Client created successfully")
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .build()
            );
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("user", userRequest))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );}
    }


    @PostMapping("/technicien")
    public ResponseEntity<HttpResponse> addTechnicien(@Valid  @RequestBody UserRequest userRequest) {

        try {

            userRequest.setRoleName("TECHNICIEN");
            User newUser = userService.saveUser(userRequest);

            return ResponseEntity.created(URI.create("")).body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("user", newUser))
                            .message("Technicien created successfully")
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .build()
            );
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("user", userRequest))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );}
    }


    @PostMapping("/admin")
    public ResponseEntity<HttpResponse> addAdmin(@RequestBody  @Valid UserRequest userRequest) {

        try {

            userRequest.setRoleName("TECHNICIEN");
            User newUser = userService.saveUser(userRequest);

            return ResponseEntity.created(URI.create("")).body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("user", newUser))
                            .message("Admin created successfully")
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .build()
            );
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("user", userRequest))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );}
    }




    @GetMapping("/verify")
    public ResponseEntity<HttpResponse> confirmUserAccount(@RequestParam("token") String token) {
        Boolean isSuccess = userService.verifyToken(token);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Success", isSuccess))
                        .message("Acoount Verified ")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }




    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("user", user))
                        .message("User found successfully")
                        .status(HttpStatus.FOUND)
                        .statusCode(HttpStatus.FOUND.value())
                        .build()
        );
    }


    @PutMapping("update/{id}")
    public ResponseEntity<HttpResponse> updateUser(@PathVariable Long id, @Valid @RequestBody  UserRequest userRequest) {
        try {

            User user = userService.updateUser(id, userRequest);
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("user", user))
                            .message("User updated successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("user", userRequest))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );}
    }

    @GetMapping("/findbyrole/{role}")
    public ResponseEntity<HttpResponse> getAllUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("users", userService.findAllUsersByRole(role)))
                        .message("Users found successfully")
                        .status(HttpStatus.FOUND)
                        .statusCode(HttpStatus.FOUND.value())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getAllUsers() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("users", userService.findAllUsers()))
                        .message("Users found successfully")
                        .status(HttpStatus.FOUND)
                        .statusCode(HttpStatus.FOUND.value())
                        .build()
        );
    }

    @PutMapping("/switchstatus/{id}")
    public ResponseEntity<HttpResponse> switchUserStatus(@PathVariable Long id) {
        userService.switchUserStatus(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Success", true))
                        .message("User status switched successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/top8")
    public ResponseEntity<HttpResponse> getTop8Users() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("users", userService.findTop8ByOrderByCreatedAtDesc()))
                        .message("Users found successfully")
                        .status(HttpStatus.FOUND)
                        .statusCode(HttpStatus.FOUND.value())
                        .build()
        );
    }



    @GetMapping("/count/{role}")
    public ResponseEntity<HttpResponse> countByRole(@PathVariable String role) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("count", userService.countByRoles(role)))
                        .message("Users found successfully")
                        .status(HttpStatus.FOUND)
                        .statusCode(HttpStatus.FOUND.value())
                        .build()
        );
    }


    @GetMapping("/count")
    public ResponseEntity<HttpResponse> countUsers() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("count", userService.userCount()))
                        .message("Users found successfully")
                        .status(HttpStatus.FOUND)
                        .statusCode(HttpStatus.FOUND.value())
                        .build()
        );
    }


    @GetMapping("/findbyemail/{email}")
    public ResponseEntity<HttpResponse> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("user", user))
                        .message("User found successfully")
                        .status(HttpStatus.FOUND)
                        .statusCode(HttpStatus.FOUND.value())
                        .build()
        );
    }


}
