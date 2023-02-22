package ru.spmi.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spmi.backend.entities.Person;
import ru.spmi.backend.entities.User;
import ru.spmi.backend.enums.Role;
import ru.spmi.backend.repositories.PersonRepository;
import ru.spmi.backend.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PersonRepository personRepository;

    public List<Role> getPersonRoles(Long personId) {
        return userRepository.findUsersByPersonId(personId)
                .stream().map(x -> x.getRoles())
                .collect(Collectors.toList());
    }
}
