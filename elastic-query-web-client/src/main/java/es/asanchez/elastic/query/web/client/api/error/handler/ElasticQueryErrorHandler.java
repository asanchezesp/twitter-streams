package es.asanchez.elastic.query.web.client.api.error.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ElasticQueryErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticQueryErrorHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public String handler(AccessDeniedException e, Model model){
        LOG.error("Access denied", e);
        model.addAttribute("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        model.addAttribute("errorDescription", "Access denied");
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handler(IllegalArgumentException e, Model model){
        LOG.error("Illegal argument", e);
        model.addAttribute("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("errorDescription", "Illegal argument");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handler(Exception e, Model model){
        LOG.error("Error", e);
        model.addAttribute("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        model.addAttribute("errorDescription", "Error");
        return "error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handler(RuntimeException e, Model model){
        LOG.error("RuntimeException", e);
        model.addAttribute("error", "Could not get response " + e.getMessage());
        model.addAttribute("errorDescription", "Runtime exception" + e.getMessage());
        return "home";
    }

    @ExceptionHandler({BindException.class})
    public String handler(BindException e, Model model){
        LOG.error("Method argument validation Exception", e);
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(((FieldError)error).getField(), error.getDefaultMessage());
            model.addAttribute("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
            model.addAttribute("errorDescription", errors);
        });
        return "home";
    }


}
