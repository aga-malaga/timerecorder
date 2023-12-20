package com.example.timerecorder.common.dto;

import java.util.List;

public record PageDTO(int pageNumber,
                      int pageSize,
                      int totalElements,
                      List<?> content) {
}