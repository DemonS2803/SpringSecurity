package ru.spmi.backend.entities;

import lombok.Data;

@Data
public class AuthRequestDTO {

    private String login;
    private String password;
}
