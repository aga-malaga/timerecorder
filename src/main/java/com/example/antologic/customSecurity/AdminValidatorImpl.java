package com.example.antologic.customSecurity;

import com.example.antologic.common.UnauthorizedException;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
class AdminValidatorImpl implements AdminValidator {

    private final UserRepository userRepository;

    public boolean validate(final UUID uuid) {
        userRepository.findUserByUuidAndRole(uuid, Role.ADMIN)
                .orElseThrow(() -> new UnauthorizedException(
                        "User lacks valid authentication credentials for the requested resource"));

        return true;
    }
}
