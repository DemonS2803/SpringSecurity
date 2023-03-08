package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class ChooseRoleResponceDTO {
    private Set<String> roles;
    private String token;
}
