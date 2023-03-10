package spring.Storage.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Storage.dto.MyObject;
import spring.Storage.dto.PersonDTO;
import spring.Storage.exception.EmailAlreadyExistsException;
import spring.Storage.repositories.PersonRepository;
import spring.Storage.services.PersonService;
import spring.Storage.util.ApplicationExceptionHandler;


import javax.servlet.http.HttpServlet;
import javax.validation.Valid;


@RestController
public class RegistrationController extends HttpServlet {

    private final PersonService personService;

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;


    private final ApplicationExceptionHandler applicationExceptionHandler;


    @Autowired
    public RegistrationController(PersonService personService,
                                  PersonRepository personRepository,
                                  ModelMapper modelMapper,
                                  ApplicationExceptionHandler applicationExceptionHandler) {
        this.personService = personService;
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.applicationExceptionHandler = applicationExceptionHandler;
    }

    @PostMapping("/register")
    public ResponseEntity<MyObject> createPerson(@RequestBody @Valid PersonDTO personDTO) throws EmailAlreadyExistsException {
        MyObject myObject = new MyObject();
        myObject.setData("true");
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = personService.createPerson(personDTO);
        httpHeaders.add("Set-Cookie", "token=" + token);
        httpHeaders.setCacheControl("no-cache");


        return new ResponseEntity<>(myObject, httpHeaders, HttpStatus.OK);
    }


}



