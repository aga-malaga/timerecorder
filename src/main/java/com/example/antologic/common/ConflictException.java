package com.example.antologic.common;

import java.io.Serial;

public class ConflictException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ConflictException(String msg) {
        super(msg);
    }
}
