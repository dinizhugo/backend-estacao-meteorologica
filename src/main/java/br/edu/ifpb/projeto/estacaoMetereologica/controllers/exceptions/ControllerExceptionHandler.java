package br.edu.ifpb.projeto.estacaoMetereologica.controllers.exceptions;

import br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions.InvalidMonthException;
import br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions.ResultsNotFoundException;
import br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions.StationNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<StandardError> stationNotFoundException(StationNotFoundException e, HttpServletRequest request) {
        String error = "Station not found.";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> dateInvalidException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String error = "Date invalid.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidMonthException.class)
    public ResponseEntity<StandardError> invalidMonthException(InvalidMonthException e, HttpServletRequest request) {
        String error = "Invalid month.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ResultsNotFoundException.class)
    public ResponseEntity<StandardError> resultsNotFoundException(ResultsNotFoundException e, HttpServletRequest request) {
        String error = "Results not found.";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
