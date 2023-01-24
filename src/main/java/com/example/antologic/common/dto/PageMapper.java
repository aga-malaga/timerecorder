package com.example.antologic.common.dto;

import com.example.antologic.project.dto.ProjectDTOBudget;
import com.example.antologic.timeRecord.dto.TimeRecordDTO;
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

    public static PageDTO toDtoProject(Page<ProjectDTOBudget> page) {
        return new PageDTO(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getContent());
    }

    public static PageDTO toDtoTR(Page<TimeRecordDTO> page) {
        return new PageDTO(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getContent());
    }
}

