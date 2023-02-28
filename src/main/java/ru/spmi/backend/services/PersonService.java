package ru.spmi.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spmi.backend.repositories.PersonRepository;
import ru.spmi.backend.repositories.UserRepository;

@Service
public class PersonService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PersonRepository personRepository;


}
