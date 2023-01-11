package com.example.antologic.customSecurity;

import com.example.antologic.common.UnauthorizedException;
import com.example.antologic.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
class ValidateAdmin implements AdminValidator {

    private final UserRepository userRepository;

    public boolean validateAdmin(final UUID uuid) {
        if (!userRepository.findByUuid(uuid).get().getRole().name().equals("ADMIN")) {
            throw new UnauthorizedException("User lacks valid authentication credentials for the requested resource");
        }

        return true;
    }
}
