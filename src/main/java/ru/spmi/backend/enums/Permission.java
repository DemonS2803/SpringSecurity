package ru.spmi.backend.enums;

public enum Permission {
    PERMISSION_WRITE("permission-write"),
    PERMISSION_CREATE("permission-create"),
    PERMISSION_READ("permission-read");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
