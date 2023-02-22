package ru.spmi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.Person;
import ru.spmi.backend.entities.User;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    ArrayList<User> findUsersByPersonId(Long personId);
    User findUserByLoginAndPassword(String login, String password);
}