package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spmi.backend.entities.DRolesEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class UserDTO {

    //это тоже не используется, но оно потенциально полезно
    private String login;

    // не использующееся поле
    private String password;
    private String token;
}
