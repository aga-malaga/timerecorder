package com.example.timerecorder.project.dto;

import com.example.timerecorder.common.Role;

import java.math.BigDecimal;

public record SearchCriteria(
        String login,
        String name,
        String surname,
        BigDecimal costFrom,
        BigDecimal costTo,
        Role role) {
}
