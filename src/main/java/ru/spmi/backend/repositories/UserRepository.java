package ru.spmi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.UsersEntity;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    ArrayList<UsersEntity> findAll();
    Optional<UsersEntity> findUsersEntityByLogin(String login);
    Optional<UsersEntity> findUsersEntityByLoginAndPassword(String login, String password);
    ArrayList<UsersEntity> findAllByPersonId(Long personId);


//    @Query("SELECT ur.role_id from users_roles ur where ur.user_id = ?1")
//    ArrayList<Long> findRoles(Long userId);

}
