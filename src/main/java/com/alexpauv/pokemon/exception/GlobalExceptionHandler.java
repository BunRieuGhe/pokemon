package com.alexpauv.pokemon.exception;

import com.alexpauv.pokemon.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MyUsernameNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUsernameNotFound(MyUsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO(ErrorCode.USERNAME_NOT_FOUND));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleUsernameAlreadyExists(UsernameAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(ErrorCode.USERNAME_ALREADY_EXISTS));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(ErrorCode.EMAIL_ALREADY_EXISTS));
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorDTO> handleAuthenticationFailed(AuthenticationFailedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO(ErrorCode.AUTHENTICATION_FAILED));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleRoleNotFound(RoleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(ErrorCode.ROLE_NOT_FOUND));
    }
}
