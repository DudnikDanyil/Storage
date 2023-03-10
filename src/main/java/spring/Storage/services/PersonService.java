package spring.Storage.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.Storage.dto.InfoPersonDTO;
import spring.Storage.dto.PersonDTO;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.EmailAlreadyExistsException;
import spring.Storage.exception.InvalidUsernameOrPasswordException;
import spring.Storage.models.Person;
import spring.Storage.models.User_Data;
import spring.Storage.repositories.PersonRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    private final ConfigService configService;


    @Autowired
    public PersonService(PersonRepository personRepository,
                         ModelMapper modelMapper, ConfigService configService) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.configService = configService;
    }

    // Автризация
    public ResponseEntity<List<InfoPersonDTO>> findByUserNameAndPassword(PersonDTO personDTO) throws InvalidUsernameOrPasswordException, AbsentPersonIdException {

        Person personToAdd = convertToPerson(personDTO);

        String password = personToAdd.getPassword();
        String password2 = passwordEncryptionMD5(password);
        String email = personToAdd.getEmail();

        if (email != "" && password != "") {

            if (personRepository.findByEmailAndPassword(email, password2).isPresent()) {

                Person person = personRepository.findAllByEmail(email);

                String token = createJWTToken(personToAdd);

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("Set-Cookie", "token=" + token);
                httpHeaders.setCacheControl("no-cache");

                List<InfoPersonDTO> PersonListInf = getInfoDuringRegistration(person.getId());
                List<InfoPersonDTO> PersonListInf2 = listFillingInfoPersonDTO(PersonListInf);

                return new ResponseEntity<>(listFillingInfoPersonDTO(PersonListInf2), httpHeaders, HttpStatus.OK);
            } else {
                throw new InvalidUsernameOrPasswordException("Incorrect username or password!");
            }
        }
        throw new InvalidUsernameOrPasswordException("Empty username and password!");
    }

    public List<InfoPersonDTO> listFillingInfoPersonDTO(List<InfoPersonDTO> PersonListInf) {
        List<InfoPersonDTO> PersonListInf2 = new ArrayList<>();
        for (InfoPersonDTO personInf : PersonListInf) {
            personInf.setData("true");
            PersonListInf2.add(personInf);
        }
        return PersonListInf2;
    }
    public List<InfoPersonDTO> ExaminationJWTToken(HttpServletRequest request) throws AbsentPersonIdException {

        if (request.getCookies() != null) {

            Cookie[] cookies = request.getCookies();

            return getInfoPersonWithToken(configService.decodingJWTToken(cookies));
        }

        InfoPersonDTO infoPersonDTO = new InfoPersonDTO();
        return configService.generatingPathObjectWithDataFalse(infoPersonDTO);
    }

//    public String decodingJWTToken(Cookie[] cookies) throws AbsentPersonIdException {
//
//                    String UserIdCookie = "";
//
//            for (Cookie c : cookies) {
//
//                UserIdCookie = c.getValue();
//            }
//
//            System.out.println(UserIdCookie);
//
//            DecodedJWT decodedJWT;
//
//            Algorithm algorithm = Algorithm.HMAC256("ACB");
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .build();
//            decodedJWT = verifier.verify(UserIdCookie);
//
//            Claim claim = decodedJWT.getClaim("userId");
//            String userId = claim.asString();
//
//            System.out.println(userId);
//
//            return userId;
//    }

//    public List<InfoPersonDTO> generatingPathObjectWithDataFalse(InfoPersonDTO infoPersonDTO) {
//        infoPersonDTO.setData("false");
//        List<InfoPersonDTO> inf = new ArrayList<>();
//        inf.add(infoPersonDTO);
//
//        return inf;
//    }

    public List<InfoPersonDTO> getInfoPersonWithToken(String userId) throws AbsentPersonIdException {

        if (!personRepository.findPersonById(Integer.parseInt(userId)).isEmpty()) {

            List<InfoPersonDTO> InfoPerson = getInfoDuringRegistration(Integer.parseInt(userId));

            if (InfoPerson.isEmpty()) {

                throw new AbsentPersonIdException("");
            } else {

                return listFillingInfoPersonDTO(InfoPerson);
            }
        } else {
            InfoPersonDTO infoPersonDTO = new InfoPersonDTO();
            return configService.generatingPathObjectWithDataFalse(infoPersonDTO);
        }
    }


    // Регистрируем пользователя и возвращаем в ответ его id
    @Transactional
    public String createPerson(PersonDTO personDTO) throws EmailAlreadyExistsException {
        Person personToAdd = convertToPerson(personDTO);

        String password = personToAdd.getPassword();
        String password2 = passwordEncryptionMD5(password);
        personToAdd.setPassword(password2);

        if (personRepository.findByEmail(personToAdd.getEmail()).isEmpty()) {

            personToAdd.setPassword(personToAdd.getPassword());
            personToAdd.setEmail(personToAdd.getEmail());
            personRepository.save(personToAdd);

            return createJWTToken(personToAdd);
        } else {
            throw new EmailAlreadyExistsException("This user already exists! Please login.");
        }

    }

    public String passwordEncryptionMD5(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();

            return myHash;
        } catch (Throwable e){
            return null;
        }
    }

    @Transactional
    public String createJWTToken(Person personToAdd) {

        Person person = personRepository.findPersonByEmail(personToAdd.getEmail());

        Algorithm algorithm = Algorithm.HMAC256("ACB");
        String token = JWT.create()
                .withClaim("userId", Integer.toString(person.getId()))
                .sign(algorithm);

        return token;
    }

    public List<InfoPersonDTO> getInfoDuringRegistration(int userId) throws AbsentPersonIdException{

        Person person = personRepository.findAllById(userId);

        List<User_Data> PersonList = person.getInformation();

        if(PersonList.isEmpty()) {
            List<InfoPersonDTO> listInf = new ArrayList<>();
            InfoPersonDTO infoPersonDTO = new InfoPersonDTO();
            listInf.add(infoPersonDTO);

            return listFillingInfoPersonDTO(listInf);

//            throw new AbsentPersonIdException("");

        } else {
            return PersonList.stream().map(this::convertToInfoPersonDTO).collect(Collectors.toList());
        }
    }

    // Методы для конвертации
    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    public PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    public InfoPersonDTO convertToInfoPersonDTO(User_Data user_data) {
        return modelMapper.map(user_data, InfoPersonDTO.class);
    }
    public InfoPersonDTO convertToInfoPersonDTO(Person person) {
        return modelMapper.map(person, InfoPersonDTO.class);
    }
}


