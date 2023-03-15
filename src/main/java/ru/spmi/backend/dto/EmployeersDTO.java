package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spmi.backend.entities.EmployeeResponse;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeersDTO {
    private ArrayList<EmployeeResponse> employees;
}
