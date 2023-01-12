package com.example.antologic.filter;

import com.example.antologic.user.Role;

import java.math.BigDecimal;

public record SearchCriteria(
        String login,
        String name,
        String surname,
        BigDecimal costFrom,
        BigDecimal costTo,
        Role role) {
}
