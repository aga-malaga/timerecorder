package com.example.timerecorder.common.dto;

import com.example.timerecorder.project.dto.ProjectDtoBudget;
import com.example.timerecorder.timeRecord.dto.TimeRecordDto;
import com.example.timerecorder.user.dto.UserDto;
import org.springframework.data.domain.Page;

public final class PageMapper {

    public static PageDTO toDtoUser(Page<UserDto> page) {
        return new PageDTO(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getContent());
    }

    public static PageDTO toDtoProject(Page<ProjectDtoBudget> page) {
        return new PageDTO(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getContent());
    }

    public static PageDTO toDtoTR(Page<TimeRecordDto> page) {
        return new PageDTO(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getContent());
    }
}