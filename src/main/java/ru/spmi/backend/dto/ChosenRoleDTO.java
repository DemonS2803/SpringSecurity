package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChosenRoleDTO {

    // этот дто можно упразднить (заменить любым общим дто с 1 стринг полем)
    private String role;
}
