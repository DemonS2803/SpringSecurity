package ru.spmi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.EmployeeResponse;
import ru.spmi.backend.entities.UsersEntity;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    ArrayList<UsersEntity> findAll();
    Optional<UsersEntity> findUsersEntityByLoginAndPassword(String login, String password);
    Optional<UsersEntity> findUsersEntityByLogin(String login);

    ArrayList<UsersEntity> findAllByPersonId(Long personId);
    Optional<UsersEntity> findUsersEntityByPersonIdAndRoles(Long personId, String role);

 
//    @Query("SELECT fio FROM vf_test_complete_json_person(:filters::Json, :page_rows, :page_num)")
    @Query(value = "SELECT * FROM p")
    ArrayList<EmployeeResponse> listEployeers(String filters, int page_rows, int page_num);

}
