package ru.spmi.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spmi.backend.enums.Role;


@Entity
@Builder
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Person person;
    private String username;
    private String login;
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role roles;

}
