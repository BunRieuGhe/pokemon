package com.alexpauv.pokemon.exception;

import com.alexpauv.pokemon.dto.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto(ErrorCode.INTERNAL_ERROR, e.getMessage()));
    }

    @ExceptionHandler(CustomBadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentials(CustomBadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(ErrorCode.BAD_CREDENTIALS, e.getMessage()));
    }

    @ExceptionHandler(CustomAccountLockedException.class)
    public ResponseEntity<ErrorDto> handleAccountLocked(CustomAccountLockedException e) {
        return ResponseEntity.status(HttpStatus.LOCKED).body(new ErrorDto(ErrorCode.ACCOUNT_LOCKED, e.getMessage()));
    }

    @ExceptionHandler(AccountDeadException.class)
    public ResponseEntity<ErrorDto> handleAccountDead(AccountDeadException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(ErrorCode.ACCOUNT_DEAD, e.getMessage()));
    }

    @ExceptionHandler(PasswordResetEmailSendingFailureException.class)
    public ResponseEntity<ErrorDto> handlePasswordResetEmailSendingFailure(PasswordResetEmailSendingFailureException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto(ErrorCode.PASSWORD_RESET_EMAIL_SENDING_FAILURE, e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordResetTokenException.class)
    public ResponseEntity<ErrorDto> handleInvalidPasswordResetToken(InvalidPasswordResetTokenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(ErrorCode.INVALID_PASSWORD_RESET_TOKEN, e.getMessage()));
    }

    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<ErrorDto> handleWeakPassword(WeakPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(ErrorCode.WEAK_PASSWORD, e.getMessage()));
    }

    @ExceptionHandler(CustomUsernameNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUsernameNotFound(CustomUsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(ErrorCode.USERNAME_NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleUsernameAlreadyExists(UsernameAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(ErrorCode.USERNAME_ALREADY_EXISTS, e.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(ErrorCode.EMAIL_ALREADY_EXISTS, e.getMessage()));
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEmailNotFound(EmailNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(ErrorCode.EMAIL_NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorDto> handleRoleNotFound(RoleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ErrorCode.ROLE_NOT_FOUND, e.getMessage()));
    }
}
