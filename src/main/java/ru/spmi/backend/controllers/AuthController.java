package ru.spmi.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.entities.AuthRequestDTO;
import ru.spmi.backend.entities.JwtResponse;
import ru.spmi.backend.security.JwtUtils;
import ru.spmi.backend.services.UserDAO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private JwtUtils jwtUtils;

    private List<String> rolesToChoose;
    private String lastChosenRole;


    @GetMapping("/signin")
    public String signinPage(Model model) {
        model.addAttribute("authDTO", new AuthRequestDTO());
        return "signin";
    }

    @PostMapping("/signin")
    public String authenticateUser( AuthRequestDTO loginRequest, Model model) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(model.getAttribute("authDTO"));
        System.out.println(userDAO.toSha1(loginRequest.getPassword()));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), userDAO.toSha1(loginRequest.getPassword())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("token generated");

        List<String> roles = authentication.getAuthorities().stream().map(x -> x.getAuthority())
                .collect(Collectors.toList());

        if (roles.size() < 2) {
            System.out.println("roles < 2 -> success");
            model.addAttribute("chosenRole", roles.get(0));
            System.out.println(model.getAttribute("chosenRole"));
            return "success";

        } else {
            model.addAttribute("rolesToChoose", roles);
            rolesToChoose = roles;
            return "redirect:/api/auth/choose_role";
        }
    }

    @GetMapping("/choose_role")
    public String chooseRolePage(Model model) {
//        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().forEach(System.out::println);
        model.addAttribute("rolesToChoose", rolesToChoose);
        model.addAttribute("chosenRole", "");
        rolesToChoose.stream().forEach(System.out::println);
        System.out.println("choose role");
        return "choose-role";
    }

    @PostMapping("/choose_role")
    public String gotChosenRolePage(Model model) {
        System.out.println("Chosen role:" + model.getAttribute("chosenRole"));
//        model.addAttribute("chosenRole", chosenRole);
//        System.out.println(chosenRole);
        return "success";
    }
}
