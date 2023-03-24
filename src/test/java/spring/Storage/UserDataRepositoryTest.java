package spring.Storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.Storage.models.Person;
import spring.Storage.models.UserData;
import spring.Storage.repositories.PersonRepository;
import spring.Storage.repositories.UserDataRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class UserDataRepositoryTest {

    private final UserDataRepository userDataRepository;
    @Autowired
    private final PersonRepository personRepository;

    @Autowired
    public UserDataRepositoryTest(UserDataRepository userDataRepository,
                                  PersonRepository personRepository) {
        this.userDataRepository = userDataRepository;
        this.personRepository = personRepository;
    }

    @Test
    @Transactional
    void deleteByUserDataIdAndNameFile_Test(){
        String email = "Test" + (int) Math.round(Math.random() * 500) + "@example.com";
        String password = "Password123";
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(password);

        personRepository.save(person);

        UserData userData = new UserData();
        userData.setNameFile("test" + (int) Math.round(Math.random() * 500) + ".txt"); // задайте нужное имя файла
        userData.setUserDataId(person.getId());
        userDataRepository.save(userData);

        userDataRepository.deleteByUserDataIdAndNameFile(userData.getUserDataId(), userData.getNameFile());

        assertFalse(userDataRepository.existsById(userData.getUserDataId()));
    }

//    @Test
//    @Transactional
//    void findAllByUserDataIdAndNameFileStartingWith_Test(){
//        String email = "Test" + (int) Math.round(Math.random() * 500) + "@example.com";
//        String password = "Password123";
//        Person person = new Person();
//        person.setEmail(email);
//        person.setPassword(password);
//
//        personRepository.save(person);
//
//        UserData userData = new UserData();
//        userData.setNameFile("test" + (int) Math.round(Math.random() * 500) + ".txt"); // задайте нужное имя файла
//        userData.setUserDataId(person.getId());
//        userDataRepository.save(userData);
//
//        Assertions.assertNotEmpty(userDataRepository.findAllByUserDataIdAndNameFileStartingWith(person.getId(), userData.getNameFile()));
//    }
//

}
