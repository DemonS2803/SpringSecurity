package ru.spmi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.PersonUsersEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonUsersEntity, Long> {



}
