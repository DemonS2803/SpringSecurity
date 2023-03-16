package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponceDTO {

    // нужно ли выбирать роль (исп. только когда есть несколько ролей)
    private boolean needToChooseRole;

    //собственно список с ролями
    private Set<String> roles;

    // обЪект в котором лежат токен и логин (пароль можно заменить на любые другие данные, он не исп.)
    private UserDTO user;
}
