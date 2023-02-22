package ru.spmi.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spmi.backend.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String login;
    private String password;
    private String username;
    private Role role;
}
