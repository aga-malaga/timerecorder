package com.example.antologic.customSecurity;

import com.example.antologic.user.Role;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
class ValidateEnum implements Validator{

    private final List<String> list = List.of(
            Role.EMPLOYEE.name(),
            Role.ADMIN.name(),
            Role.MANAGER.name()
    );

    public boolean validateEnum(String role){
        if (list.contains(role)){
            return true;
        } else {
            throw new RuntimeException(role + " this role does not exist");
        }
    }
}
