package org.elkastali.usericroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.elkastali.usericroservice.entities.Confirmation;
import org.elkastali.usericroservice.entities.Role;

import org.elkastali.usericroservice.entities.User;
import org.elkastali.usericroservice.model.UserRequest;
import org.elkastali.usericroservice.repository.ConfirmationRepository;
import org.elkastali.usericroservice.repository.RoleRepository;
import org.elkastali.usericroservice.repository.UserRepository;
import org.elkastali.usericroservice.service.EmailService;
import org.elkastali.usericroservice.service.UserService;
import org.elkastali.usericroservice.utils.EmailUtils;
import org.elkastali.usericroservice.utils.PasswordUtils;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service

public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;


    // SAVE USER
    @Override
    public User saveUser(UserRequest userRequest) {
        System.out.println(userRequest.getIsActive());
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        // MAP UserRequest To User
        User user = mapUserRequestToUser(userRequest);

        // SAVE USER
        user.setPassword(PasswordUtils.generatePassword());
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));


        //user.setIsActive(userRequest.isActive());
        System.err.println(user.getIsActive());
        user.setIsAdmin(false);
        user.setIsStaff(false);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        // SAVE CONFIRMATION
        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);
        user.setPassword(password);

        //TODO  send email to user
        emailService.sendSimpleMailMessage(user, confirmation.getToken());

        return user;
    }

    // VERIFY TOKEN
    @Override
    public Boolean verifyToken(String token) {
        // FIND THE CONFIRMATION BY TOKEN
        Confirmation confirmation = confirmationRepository.findByToken(token);

        // FIND THE USER BY EMAIL IN CONFIRMATION
        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        //SAVE USER
        user.setIsActive(true);
        userRepository.save(user);
        // UNCOMMENT THIS LINE TO DELETE THE CONFIRMATION
        //confirmationRepository.delete(confirmation);
        return Boolean.TRUE;
    }

    @Override
    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public User updateUser(Long id, UserRequest userRequest) {
        System.err.println("the id" + id);
        /*if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new RuntimeException("Email already exists");
        }*/
        User userByEmail = userRepository.findByEmailContainingIgnoreCase(userRequest.getEmail());

        // User userByEmail=userRepository.findByEmailContainingIgnoreCase(userRequest.getEmail());

        if (userByEmail != null && userByEmail.getId() != id) {
            throw new RuntimeException("Email already exists");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByNameIgnoreCase(userRequest.getRoleName());
        if (user != null) {
            user.setNom(userRequest.getNom());
            user.setPrenom(userRequest.getPrenom());
            user.setTel(userRequest.getTel());
            user.setPhoto(userRequest.getPhoto());
            user.setEmail(userRequest.getEmail());
            user.setIsActive(userRequest.getIsActive());
            //user.getRoles().clear();
            //user.getRoles().add(role);


        }
        //SEND MAIL
        emailService.sendMail(user, EmailUtils.getUpdateMessage(user));
        return userRepository.save(user);

    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllUsersByRole(String role) {
        List<User> users = userRepository.findAll();
        //users=users.stream().filter(user -> user.getRoles().contains(roleRepository.findByNameIgnoreCase(role))).toList();

        List<User> utilisateursActifsAvecRole = users.stream()
                .filter(user -> user.getRoles().contains(roleRepository.findByNameIgnoreCase(role)))
                .filter(user -> user.getIsActive() == true) // Assurez-vous que la variable isActif est true
                .collect(Collectors.toList());
        return utilisateursActifsAvecRole;
    }

    @Override
    public void switchUserStatus(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setIsActive(!user.getIsActive());
            userRepository.save(user);
        }

    }

    @Override
    public List<User> findTop8ByOrderByCreatedAtDesc() {
        return userRepository.findTop8ByOrderByCreatedAtDesc();
    }

    @Override
    public Long countByRoles(String role) {
        return userRepository.countByRoles(roleRepository.findByNameIgnoreCase(role));
    }

    @Override
    public Long userCount() {
        return userRepository.userCount();
    }


    // MAP UserRequest To User
    private User mapUserRequestToUser(UserRequest userRequest) {
        Role role = roleRepository.findByNameIgnoreCase(userRequest.getRoleName());

        User user = new User();
        user.setNom(userRequest.getNom());
        user.setPrenom(userRequest.getPrenom());
        user.setTel(userRequest.getTel());
        user.setPhoto(userRequest.getPhoto());
        user.setEmail(userRequest.getEmail());
        user.setIsActive(userRequest.getIsActive());
        user.setUsername( userRequest.getEmail());
        user.getRoles().add(role);
        return user;

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    @Override
    public User findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }
}

