package org.elkastali.usericroservice.service;



import org.elkastali.usericroservice.entities.User;
import org.elkastali.usericroservice.model.UserRequest;
import org.springframework.stereotype.Component;


import java.util.List;



public interface UserService {

    User findByUsername(String username);
    User findByEmailIgnoreCase(String email);

    User saveUser(UserRequest userRequest);
    Boolean verifyToken(String token);

    User findUserById(Long id);

    User updateUser(Long id,UserRequest userRequest);

    List<User> findAllUsers();

    List<User> findAllUsersByRole(String role);


    public void switchUserStatus(Long id);

    List<User> findTop8ByOrderByCreatedAtDesc();

    Long countByRoles(String role);

    Long userCount();

    User findByEmail(String email);
}
