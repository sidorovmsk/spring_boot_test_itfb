package spring_boot_java.test_itfb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import spring_boot_java.test_itfb.exceptions.AuthorNotFoundException;
import spring_boot_java.test_itfb.exceptions.BookNotFoundException;
import spring_boot_java.test_itfb.exceptions.PersonNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleBookNotFoundException(BookNotFoundException ex) {
        log.info("handleBookNotFoundException: " + ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleAuthorNotFoundException(AuthorNotFoundException ex) {
        log.info("handleAuthorNotFoundException: " + ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handlePersonNotFoundException(PersonNotFoundException ex) {
        log.info("handlePersonNotFoundException: " + ex.getMessage());
        return ex.getMessage();
    }
}