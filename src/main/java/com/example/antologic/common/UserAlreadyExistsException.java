package com.example.antologic.common;

import java.io.Serial;

public class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
