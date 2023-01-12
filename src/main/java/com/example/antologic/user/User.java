package com.example.antologic.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Column(name = "uuid")
    private final UUID uuid = UUID.randomUUID();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "cost_per_hour")
    private BigDecimal costPerHour;

    public User(final String login, final String name, final String surname, final Role role, final String email, final String password, final BigDecimal costPerHour) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.email = email;
        this.password = password;
        this.costPerHour = costPerHour;
    }


}
