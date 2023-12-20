package com.example.timerecorder.customSecurity;

import com.example.timerecorder.common.Role;
import org.springframework.stereotype.Service;

@Service
class EnumValidatorImpl implements EnumValidator {

    @Override
    public void validateEnum(Role role) {
        if (!role.isRole()) {
            throw new RuntimeException(role + " this role does not exist");
        }
    }
}