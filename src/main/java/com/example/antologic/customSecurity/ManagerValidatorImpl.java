package com.example.antologic.customSecurity;

import com.example.antologic.common.UnauthorizedException;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@AllArgsConstructor
class ManagerValidatorImpl implements ManagerValidator {

    private final UserRepository userRepository;

    public boolean validateManager(UUID uuid){
        userRepository.findUserByUuidAndRole(uuid, Role.MANAGER)
                .orElseThrow(() -> new UnauthorizedException(
                        "User lacks valid authentication credentials for the requested resource"));

        return true;
    }
}
