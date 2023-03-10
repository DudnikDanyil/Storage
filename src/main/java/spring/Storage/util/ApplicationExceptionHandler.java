package spring.Storage.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.EmailAlreadyExistsException;
import spring.Storage.exception.FileUploadException;
import spring.Storage.exception.InvalidUsernameOrPasswordException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public Map<String, String> EmailException(EmailAlreadyExistsException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMassage", ex.getMessage());
        errorMap.put("data", "false");
        return errorMap;
    }

 //   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileUploadException.class)
    public Map<String, String> FileException(FileUploadException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMassage", ex.getMessage());
        errorMap.put("data", "false");
        return errorMap;
    }

 //   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AbsentPersonIdException.class)
    public List<Map<String,String>> CookieException(AbsentPersonIdException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("data", "true");
        List<Map<String,String>> list = new ArrayList<>();
            list.add(errorMap);
            return list;
    }

//    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    public Map<String, String> PasswordEmailException(InvalidUsernameOrPasswordException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMassage", ex.getMessage());
        errorMap.put("data", "false");
        return errorMap;
    }

}
