package ru.spmi.backend.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.spmi.backend.enums.Permission;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtConfigurer jwtConfigurer;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtConfigurer jwtConfigurer,
                          @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.jwtConfigurer = jwtConfigurer;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/action/create").hasAuthority(Permission.PERMISSION_CREATE.getPermission())
                        .requestMatchers("/action/write").hasAuthority(Permission.PERMISSION_WRITE.getPermission())
                        .requestMatchers("/action/read").hasAuthority(Permission.PERMISSION_READ.getPermission())
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/test/**").permitAll()
                        .anyRequest().authenticated()
                )
                .apply(jwtConfigurer);

        return http.build();
    }

    @Bean
    protected AuthenticationManager authenticationManager() {
        var dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder());
        dao.setUserDetailsService(userDetailsService);
        return new ProviderManager(dao);
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
