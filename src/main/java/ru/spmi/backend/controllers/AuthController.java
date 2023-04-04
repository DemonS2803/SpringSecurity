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
        System.out.println("roles: \n" + roles);
        var responcedto = new AuthResponceDTO();
        var userdto = new UserDTO();
        userdto.setToken(jwt);
        userdto.setLogin(loginRequest.getLogin());
        responcedto.setRoles(roles);
        responcedto.setUser(userdto);

        if (roles.size() > 1) responcedto.setNeedToChooseRole(true);
        else responcedto.setNeedToChooseRole(false);

        return new ResponseEntity<>(responcedto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/get_menu")
    public String getMenu() {
        System.out.println("in getMenu");
        var response = new ResponseEntity<>(new MenuDTO("hello", "a"), HttpStatus.OK);
        System.out.println(response);
        return "{\"menu\":[{\"menu_item\":\"Журналы\",\"link\":\"url_1\",\"sorted\":\"1\", \"level\":[{\"menu_item\":\"Журнал студентов\",\"link\":\"url_1_1\",\"sorted\":\"1\"} ,{\"menu_item\":\"Журнал групп\",\"link\":\"url_1_2\",\"sorted\":\"2\"} ,{\"menu_item\":\"Журнал групп ДПК\",\"link\":\"url_1_3\",\"sorted\":\"3\"} ,{\"menu_item\":\"Журнал соискателей\",\"link\":\"url_1_4\",\"sorted\":\"4\"} ,{\"menu_item\":\"Журнал отчетов\",\"link\":\"url_1_5\",\"sorted\":\"5\"}]} ,{\"menu_item\":\"Программы и стандарты\",\"link\":\"url_2\",\"sorted\":\"2\", \"level\":[{\"menu_item\":\"Учебные планы\",\"link\":\"url_2_1\",\"sorted\":\"1\"} ,{\"menu_item\":\"НАГРУЗКА\",\"link\":\"url_2_2\",\"sorted\":\"2\", \"level\":[{\"menu_item\":\"Выписка из рабочих учебных планов\",\"link\":\"url_3_1\",\"sorted\":\"1\"}]}]} ,{\"menu_item\":\"Диссертационный совет\",\"link\":\"url_4\",\"sorted\":\"4\"}]}";
    }



    @GetMapping("/get_available_roles")
    public ResponseEntity<?> getAvailableRoles() {
        Set<String> roles = userDAO.findAllUserRoles(userDAO.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName())).stream().map(x -> x.getRoleName()).collect(Collectors.toSet());
        var responsedto = new AuthResponceDTO();
        responsedto.setRoles(roles);
        return new ResponseEntity<>(responsedto, HttpStatus.OK);
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

            // получаем текущий логин из контекста
//            String login = SecurityContextHolder.getContext().getAuthentication().getName();

            //создаем новую аутентификацию на основе текущего логина и новой роли
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken (
//                                                    userDAO.findNeedLoginByLoginAndRole(login, chosenRole.getRole()),
//                                                    userDAO.getPasswordByLogin(login)
//                                                    ));

            // устанавливаем новые данные в контекст
//            SecurityContextHolder.getContext().setAuthentication(authentication);

            // возвращаем новый токен
            return new ResponseEntity<>(new ChooseRoleResponseDTO(chosenRole.getRole(), ""), HttpStatus.ACCEPTED);
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
