package jp.mikunika.SpringBootInsurance.exception.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorEntity {

    private final HttpStatus httpStatus;

    private final LocalDateTime timestamp;

    private final String message;

    private final String details;

    public ErrorEntity(HttpStatus httpStatus, String message, String details) {
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
    }
}
