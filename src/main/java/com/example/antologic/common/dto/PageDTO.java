package com.example.antologic.common.dto;

import com.example.antologic.user.dto.UserDTO;

import java.util.List;

public record PageDTO(int pageNumber,
                      int pageSize,
                      int totalElements,
                      List<UserDTO> content) {
}
