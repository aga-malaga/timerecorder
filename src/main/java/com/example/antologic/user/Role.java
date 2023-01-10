package com.example.antologic.user;

public enum Role {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    EMPLOYEE("EMPLOYEE");

    private final String role;

    Role(final String role) {
        this.role = role;
    }

    String getRole(){
        return role;
    }
}
