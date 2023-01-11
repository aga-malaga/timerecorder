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
@Table(name = "users_table")
public class User {

    @Column(name = "users_uuid")
    private final UUID uuid = UUID.randomUUID();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;
    @Column(name = "users_login")
    private String login;
    @Column(name = "users_name")
    private String name;
    @Column(name = "users_surname")
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "users_role")
    private Role role;
    @Column(name = "users_email")
    private String email;
    @Column(name = "users_password")
    private String password;
    @Column(name = "users_cost_per_hour")
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
