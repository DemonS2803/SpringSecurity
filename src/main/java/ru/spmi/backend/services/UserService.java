package ru.spmi.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.spmi.backend.entities.Person;
import ru.spmi.backend.entities.User;
import ru.spmi.backend.entities.UserDTO;
import ru.spmi.backend.enums.Role;
import ru.spmi.backend.exceptions.UserAlreadyExistsException;
import ru.spmi.backend.repositories.PersonRepository;
import ru.spmi.backend.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonService personService;

//    public boolean saveUser(UserDTO userDTO) {
//        try {
//            if (userRepository.findUserByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword()) != null) {
//                throw new UserAlreadyExistsException();
//            }
//            System.out.println(userDTO.getRole().name());
//            Person person = Person.builder()
//                    .realUsername(userDTO.getUsername())
//                    .users(new ArrayList<>())
//                    .build();
//            personRepository.save(person);
//            User user = User.builder()
//                    .login(userDTO.getLogin())
//                    .username(userDTO.getUsername())
//                    .password(userDTO.getPassword())
//                    .roles(userDTO.getRole())
//                    .person(person)
//                    .build();
//            person.addUser(user);
//            userRepository.save(user);
//            personRepository.save(person);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    public boolean isRealUser(UserDTO userDTO) {
//        User user = userRepository.findUserByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword());
//        return user != null;
//    }
//
//    public Person getPersonByUserDTO(UserDTO userDTO) {
//        return userRepository.findUserByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword()).getPerson();
//    }




















}
