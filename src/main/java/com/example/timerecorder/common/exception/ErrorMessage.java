package com.example.timerecorder.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
    private LocalDateTime dateTime;
    private String message;
    private String description;
}