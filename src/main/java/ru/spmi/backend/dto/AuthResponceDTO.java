package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponceDTO {
    private boolean needToChooseRole;
    private Set<String> roles;
    private UserDTO user;
}
//4
//        гвоздь
//        шуруп
//        краска синяя
//        ведро для воды
//        3
//        краска
//        корыто для воды
//        шуруп 3*15