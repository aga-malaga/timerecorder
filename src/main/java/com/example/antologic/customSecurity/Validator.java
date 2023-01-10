package com.example.antologic.customSecurity;

import org.springframework.stereotype.Component;

@Component
public interface Validator {

    boolean validateEnum(String role);
}
