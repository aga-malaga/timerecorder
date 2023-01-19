package com.example.antologic.common.dto;

import com.example.antologic.user.dto.UserDTO;
import org.springframework.data.domain.Page;

public class PageMapper {

    public static PageDTO toDto(Page<UserDTO> page) {
        return new PageDTO(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getContent());
    }
}
