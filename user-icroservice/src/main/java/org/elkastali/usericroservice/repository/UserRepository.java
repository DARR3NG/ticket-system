package org.elkastali.usericroservice.repository;

import org.elkastali.usericroservice.entities.Role;
import org.elkastali.usericroservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByEmailIgnoreCase(String email);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByEmailContainingIgnoreCase(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'CLIENT' ORDER BY u.createdAt DESC limit 8")

    List<User> findTop8ByOrderByCreatedAtDesc();

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.name = 'CLIENT'")
    Long countByRoles(Role role);

    @Query("SELECT COUNT(u) FROM User u")
    Long userCount();


}
