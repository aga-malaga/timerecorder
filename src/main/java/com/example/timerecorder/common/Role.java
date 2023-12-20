package com.example.timerecorder.common;

public enum Role {
    ADMIN, MANAGER, EMPLOYEE;

    public boolean isRole() {
        return this.equals(ADMIN) || this.equals(MANAGER) || this.equals(EMPLOYEE);
    }
}