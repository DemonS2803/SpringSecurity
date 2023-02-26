package ru.spmi.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spmi.backend.entities.User;
import ru.spmi.backend.exceptions.UserLoginNotFoundException;
import ru.spmi.backend.repositories.UserRepository;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        System.out.println("userDetailsImpl");
        User user = userRepository.findUserByLogin(login).orElseThrow(() -> new UsernameNotFoundException("user doesnt excists"));
        return SecurityUser.fromUser(user);
    }
}
