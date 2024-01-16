package org.elkastali.usericroservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
@SuperBuilder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 20)
    @NotBlank(message = "Le nom ne doit pas être vide")
    @NotEmpty(message = "Le nom ne doit pas être vide")
    private String nom;
    @Column(nullable = false,length = 20)
    @NotBlank(message = "Le prenom ne doit pas être vide")
    @NotEmpty(message = "Le prenom ne doit pas être vide")
    private String prenom;
    @Email
    @NotEmpty(message = "L'email ne doit pas être vide")
    @Column(unique = true,nullable = false)
    private String email;
    private String password;

    private String photo;

    @Column(length = 10)
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Le numéro de téléphone doit contenir 10 chiffres")
    private String tel;
    private Boolean isAdmin;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime last_Login;

    private Boolean isStaff;

    @Column(nullable = false)
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    @JsonIgnore
    private List<Role> roles=new ArrayList<>();


    private String username;
}
