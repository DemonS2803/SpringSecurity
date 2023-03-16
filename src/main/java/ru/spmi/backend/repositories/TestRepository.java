package ru.spmi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.TestEntity;

import java.util.ArrayList;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {


    @Procedure("vf_test_complete")
    ArrayList<TestEntity> vf

}
