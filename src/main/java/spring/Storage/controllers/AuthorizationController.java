package spring.Storage.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Storage.dto.InfoPersonDTO;
import spring.Storage.dto.PersonDTO;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.InvalidUsernameOrPasswordException;
import spring.Storage.repositories.PersonRepository;
import spring.Storage.services.PersonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthorizationController {

    private final PersonService personService;

    private final RegistrationController registrationController;

    private final ModelMapper modelMapper;
    private final PersonRepository personRepository;

    @Autowired
    public AuthorizationController(PersonService personService, RegistrationController registrationController, ModelMapper modelMapper,
                                   PersonRepository personRepository) {
        this.personService = personService;
        this.registrationController = registrationController;
        this.modelMapper = modelMapper;
        this.personRepository = personRepository;
    }

    @PostMapping("/auth")
    public ResponseEntity<List<InfoPersonDTO>> authorization(@RequestBody PersonDTO personDTO) throws InvalidUsernameOrPasswordException, AbsentPersonIdException {

        return personService.findByUserNameAndPassword(personDTO);

    }

    @ResponseBody
    @GetMapping("/authentic")
    public List<InfoPersonDTO> authCookie(HttpServletRequest request) throws AbsentPersonIdException {
        System.out.println(request);
            return personService.examinationJWTToken(request);

    }
}




