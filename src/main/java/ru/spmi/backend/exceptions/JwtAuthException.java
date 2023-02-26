package ru.spmi.backend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class JwtAuthException extends AuthenticationException {

    private HttpStatus httpStatus;

    public JwtAuthException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public JwtAuthException(String msg) {
        super(msg);
    }
}
