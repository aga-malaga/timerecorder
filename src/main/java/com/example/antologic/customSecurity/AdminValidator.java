package com.example.antologic.customSecurity;

import java.util.UUID;

public interface AdminValidator {

    boolean validate(UUID uuid);
}
