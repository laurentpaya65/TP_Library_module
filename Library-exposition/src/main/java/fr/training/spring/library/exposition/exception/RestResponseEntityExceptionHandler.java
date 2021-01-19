package fr.training.spring.library.exposition.exception;

import fr.training.spring.library.domain.exception.NotFoundException;
import fr.training.spring.library.domain.exception.ControlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice(basePackages = {"fr.training.spring.library"})
public class RestResponseEntityExceptionHandler  {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

//    @ExceptionHandler(AdresseException.class)
//    protected ResponseEntity<String> handleAdresseException(AdresseException ex) {
//        return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
//    }

    // le message n'a pas d'interet, seul le code erreur sera utilisé par IHM pour générer un message
//    @ResponseBody
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleBibNotFoundException(final NotFoundException ex) {
        final String erreur = ex.getErrorCode();
        LOGGER.info("Erreur {} : {}",erreur,ex.getMessage());
        return erreur;
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ControlException.class)
    public String handleControlException(final ControlException ex) {
        final String erreur = ex.getErrorCode();
        LOGGER.info("Erreur {} : {}",erreur,ex.getMessage());
        return erreur;
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationsExceptions(final MethodArgumentNotValidException ex) {
        final Map<String,String> errors = new HashMap<>();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        int index = 1;
        for (ObjectError objectError : allErrors) {
            String message = objectError.getDefaultMessage();
            String fieldName = ((FieldError) objectError).getObjectName() + "_"+index;
            errors.put(fieldName,message);
            index++;
        }
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            final String fieldName = ((FieldError) error).getField();
//            final String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName,errorMessage);
//        });
        return errors;
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return super.handleMethodArgumentNotValid(ex, headers, HttpStatus.PARTIAL_CONTENT, request);
//    }
}
