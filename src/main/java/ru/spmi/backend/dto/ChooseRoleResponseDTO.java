package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ChooseRoleResponseDTO {
    private String role;
    private String token;

}
