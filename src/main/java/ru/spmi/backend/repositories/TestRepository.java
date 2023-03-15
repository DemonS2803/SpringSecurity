package ru.spmi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.TestEntity;

import java.util.ArrayList;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

    ArrayList<EmployeeResponse> listEployeers(String filters, int page_rows, int page_num);


}
