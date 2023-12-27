package com.stun106.parkapi.web.exception;
import com.stun106.parkapi.service.exception.EntityNotfoundExption;
import com.stun106.parkapi.service.exception.PasswordInvalidException;
import com.stun106.parkapi.service.exception.UsernameUniqueViolationExeception;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(UsernameUniqueViolationExeception.class)
    public ResponseEntity<ErrorMessage> usernameUniqueViolationExeception(RuntimeException ex,
                                                                        HttpServletRequest request) {
        log.error("api error", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT,ex.getMessage()));

    }

    @ExceptionHandler(EntityNotfoundExption.class)
    public ResponseEntity<ErrorMessage> entityNotFoundExption (RuntimeException ex,
                                                                        HttpServletRequest request) {
        log.error("api error", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND,ex.getMessage()));

    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException (RuntimeException ex,
                                                                        HttpServletRequest request){
        log.error("api error", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.BAD_REQUEST,ex.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> MethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result) {
        log.error("api error", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.UNPROCESSABLE_ENTITY,"Campos(s) inválidos", result));

    }
}
