package com.academycode.academycode.modules.user.enums;

public enum UserRole {
    ADMIN("admin"),
    MANAGER("manager"),
    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
