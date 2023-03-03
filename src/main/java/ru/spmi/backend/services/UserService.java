package ru.spmi.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spmi.backend.entities.DRolesEntity;
import ru.spmi.backend.entities.UsersEntity;

import java.util.*;

@Service("userDetailsService")
public class UserService implements UserDetailsService {


    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        System.out.println(login);
        ru.spmi.backend.entities.UsersEntity user = userDAO.findUserByLogin(login);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(userDAO.findAllUserRoles(user));

        List<GrantedAuthority> authorities = buildUserAuthority(Set.of(userDAO.getRoleEntityByLogin(login)));

        return buildUserForAuthentication(user, authorities);
    }

    public User buildUserForAuthentication(ru.spmi.backend.entities.UsersEntity user, List<GrantedAuthority> grantedAuthorities) {
        return new User(user.getLogin(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }

    public List<GrantedAuthority> buildUserAuthority(Set<DRolesEntity> userRoles) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        userRoles.forEach((userRole) -> grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRoleName())));
        return new ArrayList<>(grantedAuthorities);
    }

}


