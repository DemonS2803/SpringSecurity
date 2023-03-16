package ru.spmi.backend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import ru.spmi.backend.entities.EmployeeResponse;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDTO {
    private String fio;
    private String position;
}
