package com.seniorsistemas.project.config.validation.exception.handler;

import java.util.List;

import com.seniorsistemas.project.config.validation.exception.ItemIsBeingUsedInPedidoException;
import com.seniorsistemas.project.config.validation.exception.ItemIsInactiveException;
import com.seniorsistemas.project.config.validation.exception.NotFoundException;
import com.seniorsistemas.project.config.validation.exception.PedidoIsAlreadyClosedException;
import com.seniorsistemas.project.config.validation.exception.dto.ErrorDTO;
import com.seniorsistemas.project.config.validation.exception.dto.ValidationErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationErrorDTO> handleValidationError(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return errors.stream().map(ValidationErrorDTO::new).toList();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({PedidoIsAlreadyClosedException.class, ItemIsInactiveException.class, ItemIsBeingUsedInPedidoException.class})
    public ErrorDTO handleOtherValidationsErros(Exception ex) {
        return new ErrorDTO(ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDTO handleNotFoundError(NotFoundException ex) {
        return new ErrorDTO(ex.getMessage());
    }
}
