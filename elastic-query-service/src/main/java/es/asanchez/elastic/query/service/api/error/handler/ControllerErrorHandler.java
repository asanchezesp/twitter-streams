package es.asanchez.elastic.query.service.api.error.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerErrorHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handle(AccessDeniedException e){
        LOG.error("Access denied exception",e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorizedt to this resource");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException e){
        LOG.error("",e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException e){
        LOG.error("",e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception e){
        LOG.error("",e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handle(MethodArgumentNotValidException e){
        LOG.error("",e);
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> errors.put(e.getFieldError().getField(),err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
