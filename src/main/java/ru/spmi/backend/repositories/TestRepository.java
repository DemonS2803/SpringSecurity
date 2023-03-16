package ru.spmi.backend.repositories;

import com.google.gson.Gson;
import org.hibernate.dialect.PostgreSQLJsonJdbcType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.dto.PaginationResponse;
import ru.spmi.backend.entities.TestEntity;

import java.util.ArrayList;

@Repository
public interface TestRepository extends CrudRepository<TestEntity, Long> {


//    @Query(nativeQuery = true, value = "SELECT * FROM public.vf_test_complete_json_person(:filt::json, :count_rows, :num_row)")
//    ArrayList<?> paginationFunc(@Param("filt") String filt,@Param("count_rows") int count_rows,@Param("num_row") int num_row);

    @Query(nativeQuery = true, value = "SELECT * FROM fn_increment(:num)")
    Integer dbIncrement(Integer num);



    @Query(nativeQuery = true, value = "SELECT req.fio AS fio, req.positions AS positions FROM public.vf_test_complete_json_person( cast(:filters AS json), :count_rows, :num_row) req")
    ArrayList<PaginationResponse> paginationFunc(@Param("filters") String filters, @Param("count_rows") int count_rows,@Param("num_row") int num_row);
}
