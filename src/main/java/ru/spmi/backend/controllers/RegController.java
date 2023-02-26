package ru.spmi.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spmi.backend.entities.UserDTO;
import ru.spmi.backend.enums.Role;
import ru.spmi.backend.services.PersonService;
import ru.spmi.backend.services.UserService;

@Controller
public class RegController {

    @Autowired
    UserService userService;
    @Autowired
    PersonService personService;

    @GetMapping("/reg")
    public String registrationView(Model model) {
        System.out.println("reg controller");
        model.addAttribute("roles", Role.values());
        model.addAttribute("userDTO", new UserDTO());
        return "registration";
    }

    @PostMapping("/reg")
    public String saveNewUser(UserDTO userDTO, Model model) {
        try {
            userService.saveUser(userDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/reg";
        }
        return "redirect:/";
    }

//    @GetMapping("/reg")
//    public String authorisationView(Model model) {
//        model.addAttribute("userDTO", new UserDTO());
//        return "login";
//    }
//
//    @PostMapping("/reg")
//    public String authUser(UserDTO userDTO, Model model) {
//        System.out.println(userDTO.getLogin());
//        System.out.println(userDTO.getPassword());
//        if (userService.isRealUser(userDTO)) {
//            var person = userService.getPersonByUserDTO(userDTO);
//            model.addAttribute("person", person);
//            model.addAttribute("personRoles", personService.getPersonRoles(person.getId()));
//            return "user-home";
//        }
//        return "redirect:/auth";
//    }

    @GetMapping("/home")
    public String userHome() {
        return "user-home";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

}
