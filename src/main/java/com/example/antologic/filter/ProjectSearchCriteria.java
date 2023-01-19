package com.example.antologic.filter;

import com.example.antologic.user.User;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectSearchCriteria(String name,
                                    LocalDateTime start,
                                    LocalDateTime stop,
                                    List<User> list,
                                    Boolean budget) {
}
