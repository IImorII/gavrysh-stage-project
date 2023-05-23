package jp.mikunika.SpringBootInsurance.exception.handler;

import jp.mikunika.SpringBootInsurance.exception.EntityRelationsException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorEntity> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorEntity error = new ErrorEntity(HttpStatus.NOT_FOUND, "Entity does not found!", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityRelationsException.class)
    private ResponseEntity<ErrorEntity> handleEntityRelations(EntityRelationsException ex) {
        ErrorEntity error = new ErrorEntity(HttpStatus.CONFLICT, "Entity relationships error!", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    private ResponseEntity<ErrorEntity> handleEmptyResultDataAccess(EmptyResultDataAccessException ex) {
        ErrorEntity error = new ErrorEntity(HttpStatus.NOT_FOUND, "Entity does not exist!", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
