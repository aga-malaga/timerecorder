package com.example.antologic.common.dto;

import com.example.antologic.project.dto.ProjectDTO;
import com.example.antologic.user.dto.UserDTO;
import org.springframework.data.domain.Page;

public class PageMapper {

    public static PageDTO toDtoUser(Page<UserDTO> page) {
        return new PageDTO(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getContent());
    }

    public static PageDTO toDtoProject(Page<ProjectDTO> page) {
        return new PageDTO(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getContent());
    }
}
