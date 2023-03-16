package ru.spmi.backend.dto;

public interface PaginationResponse {
    String getPositions();
    String getFio();
    int getCountRows();
    int getRowNum();
}
