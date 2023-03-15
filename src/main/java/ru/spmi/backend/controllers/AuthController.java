package ru.spmi.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.dto.*;
import ru.spmi.backend.entities.DRolesEntity;
import ru.spmi.backend.security.JwtUtils;
import ru.spmi.backend.services.UserDAO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUserPage(@RequestBody @Validated AuthRequestDTO loginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        // логинимся в контекст
        Authentication authentication = authenticateUser(loginRequest.getLogin(), loginRequest.getPassword());

        //создаем новый токен
        String jwt = jwtUtils.generateJwtToken(loginRequest.getLogin(), userDAO.getRoleByLogin(loginRequest.getLogin()));

        // получаем список всех доступных этому person'у ролей
        Set<String> roles = userDAO.findAllUserRoles(userDAO.findUserByLogin(loginRequest.getLogin())).stream().map(x -> x.getRoleName()).collect(Collectors.toSet());
        var responcedto = new AuthResponceDTO();
        var userdto = new UserDTO();
        userdto.setToken(jwt);
        responcedto.setUser(userdto);
        if (roles.size() < 2) {
            responcedto.setNeedToChooseRole(false);
            return new ResponseEntity<>(responcedto, HttpStatus.ACCEPTED);

        } else {
            responcedto.setNeedToChooseRole(true);
            responcedto.setRoles(roles);
            return new ResponseEntity<>(responcedto, HttpStatus.OK);
        }
    }

    @GetMapping("/choose_role")
    public String chooseRolePage() {
        return "return choose-role page";
    }

    /*
    функция для смены роли пользователя с несколькими учетками
     */
    @PostMapping("/choose_role")
    public ResponseEntity<?> gotChosenRolePage(@RequestHeader("Authorization") String token, @RequestBody @Validated ChosenRoleDTO chosenRole) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        // проверяет можно ли перерегаться
        boolean isTokenValid = jwtUtils.validateJwtToken(token);
        boolean isRoleAllowed = userDAO.checkUserRole(jwtUtils.getUserNameFromJwtToken(token), chosenRole.getRole());

        // если можно, то создает новый токен
        if (isTokenValid && isRoleAllowed) {
            String newToken = jwtUtils.generateJwtToken(
                        userDAO.findNeedLoginByLoginAndRole(
                                SecurityContextHolder.getContext().getAuthentication().getName(),
                                chosenRole.getRole()),
                        chosenRole.getRole()
            );
            // получаем текущий логин из контекста
            String login = SecurityContextHolder.getContext().getAuthentication().getName();

            //создаем новую аутентификацию на основе текущего логина и новой роли
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken (
                                                    userDAO.findNeedLoginByLoginAndRole(login, chosenRole.getRole()),
                                                    userDAO.getPasswordByLogin(login)
                                                    ));

            // устанавливаем новые данные в контекст
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // возвращаем новый токен
            return new ResponseEntity<>(new TokenDTO(newToken), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Something went wrong....", HttpStatus.FORBIDDEN);
    }

    /*
    лезет в бд сверяться в правильноси введенного логина и пароля после чего созает аутентификацию
    и сохраняет ее в контекст, потом по ней будет осуществляться запрет в доступе к страницам
     */
    public Authentication authenticateUser(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, userDAO.toSha1(password)));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
