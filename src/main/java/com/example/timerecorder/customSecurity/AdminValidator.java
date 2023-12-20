package com.example.timerecorder.customSecurity;

import java.util.UUID;

public interface AdminValidator {
    void validate(UUID uuid);
}