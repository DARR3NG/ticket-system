package com.elkastali.ticketservice.service;

import com.elkastali.ticketservice.entities.User;

import java.util.List;

public interface UserService {

    User findUserById(Long id);
    List<User> findAllUsers();
}
