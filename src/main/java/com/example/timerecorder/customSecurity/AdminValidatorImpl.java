package com.example.timerecorder.customSecurity;

import com.example.timerecorder.common.Role;
import com.example.timerecorder.common.exception.UnauthorizedException;
import com.example.timerecorder.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class AdminValidatorImpl implements AdminValidator {

    private final UserRepository userRepository;

    @Override
    public void validate(final UUID uuid) {
        userRepository.findUserByUuidAndRole(uuid, Role.ADMIN)
                .orElseThrow(() -> new UnauthorizedException(
                        "User lacks valid authentication credentials for the requested resource"));
    }
}