package ru.spmi.backend.services;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spmi.backend.dto.EmployerDTO;
import ru.spmi.backend.entities.DRolesEntity;
import ru.spmi.backend.entities.UsersEntity;
import ru.spmi.backend.repositories.PersonRepository;
import ru.spmi.backend.repositories.RolesRepository;
import ru.spmi.backend.repositories.TestRepository;
import ru.spmi.backend.repositories.UserRepository;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDAO {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private TestRepository testRepository;

//    кодировщик
    public String toSha1(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
        msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
        return DatatypeConverter.printHexBinary(msdDigest.digest());
    }

    public boolean saveUser(UsersEntity user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        try {
            user.setPassword(toSha1(user.getPassword()));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println("error during saving user");
            return false;
        }
    }


    public UsersEntity findUserByLogin(String login) {
        return userRepository.findUsersEntityByLogin(login).get();
    }

    public UsersEntity findUserByLoginAndPassword(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userRepository.findUsersEntityByLoginAndPassword(login, toSha1(password)).get();
    }

    public ArrayList<UsersEntity> findAll() {
        return userRepository.findAll();
    }

    public Set<DRolesEntity> findAllUserRoles(UsersEntity user) {
        var personUsers = personRepository.findPersonUsersEntitiesByPersonId(user.getPersonId());
        System.out.println("got person roles: " + personUsers.size());
        var personRoles = personUsers.stream()
                                            .map(x -> rolesRepository.findDRolesEntityByRoleId(x.getRoleId()))
                                            .collect(Collectors.toSet());
        System.out.println(personRoles);
        return personRoles;
    }

    public String getRoleByLoginAndPassword(String login, String password) {
        return rolesRepository.findDRolesEntityByRoleId(Long.valueOf(userRepository.findUsersEntityByLoginAndPassword(login, password).get().getRoles())).getRoleName();
    }

    public String getRoleByLogin(String login) {
        return rolesRepository.findDRolesEntityByRoleId(Long.valueOf(userRepository.findUsersEntityByLogin(login).get().getRoles())).getRoleName();
    }

    public Set<Long> getRoleEntityByLogin(String login) {
        return personRepository.findPersonUsersEntitiesByPersonId(userRepository.findUsersEntityByLogin(login).get().getPersonId()).stream().map(x -> x.getRoleId()).collect(Collectors.toSet());
    }

    public DRolesEntity getRoleById(Long roleId) {
        return rolesRepository.findDRolesEntityByRoleId(roleId);
    }


    // применяется при смене роли - находит нужную учетную запись по роли и текущей в контексте аутентификации
    public String findNeedLoginByLoginAndRole(String login, String role) {
        UsersEntity user = userRepository.findUsersEntityByLogin(login).get();
        ArrayList<UsersEntity> usersEntities = userRepository.findAllByPersonId(user.getPersonId());
        UsersEntity userOut = usersEntities.stream().filter(x -> rolesRepository.findDRolesEntityByRoleName(role).getRoleId() == Long.parseLong(x.getRoles())).findFirst().get();
        return userOut.getLogin();
    }

    //Проверяет наличие роли у данного юзера на всех аккаунтах
    public boolean checkUserRole(String login, String role) {
        return findAllUserRoles(userRepository.findUsersEntityByLogin(login).get()).stream().map(x -> x.getRoleName()).collect(Collectors.toList()).contains(role);
    }
//    public String getUserByPidAndRole()
    public String getPasswordByLogin(String login) {
        return userRepository.findUsersEntityByLogin(login).get().getPassword();
    }

    public Long getRoleIdByRoleName(String roleName){
        return rolesRepository.findDRolesEntityByRoleName(roleName).getRoleId();
    }

    public ArrayList<EmployerDTO> getEmployersJsonFromFilters(String filters, int countRows, int numRow) {
        ArrayList<EmployerDTO> employersList = new ArrayList<>();
        var bdFuncResponse = testRepository.paginationFunc(filters, countRows, numRow);
        bdFuncResponse.stream().forEach(x -> employersList.add(new EmployerDTO(x.getFio(), x.getPositions())));
        return  employersList;
    }

}
