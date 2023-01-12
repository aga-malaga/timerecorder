package com.example.antologic.customSecurity;

import com.example.antologic.common.UnauthorizedException;
import com.example.antologic.user.Role;
import com.example.antologic.user.User;
import com.example.antologic.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
class AdminValidatorImpl implements AdminValidator {

    private final UserRepository userRepository;

    public boolean validate(final UUID uuid) {

        final User admin = userRepository.findUserByRole(Role.ADMIN);

        if (!admin.getUuid().equals(uuid)) {
            throw new UnauthorizedException("User lacks valid authentication credentials for the requested resource");
        }
        return true;
    }
}
