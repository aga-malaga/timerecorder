package com.example.antologic.user.dto;

import java.util.UUID;

public record UserDTO(UUID uuid,
                      String login) {
}
