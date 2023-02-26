package ru.spmi.backend.enums;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permission.PERMISSION_WRITE, Permission.PERMISSION_CREATE, Permission.PERMISSION_READ)),
    TEACHER(Set.of(Permission.PERMISSION_WRITE, Permission.PERMISSION_READ)),
    STUDENT(Set.of( Permission.PERMISSION_READ)),
    GUEST(Set.of());

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
