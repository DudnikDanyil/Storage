package spring.Storage;

import javax.persistence.EntityManager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import spring.Storage.models.Person;
import spring.Storage.models.UserData;
import spring.Storage.repositories.PersonRepository;
import spring.Storage.repositories.UserDataRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class UserDataRepositoryTest {

    private final UserDataRepository userDataRepository;

    private final PersonRepository personRepository;

    private final EntityManager entityManager;

    @Autowired
    public UserDataRepositoryTest(UserDataRepository userDataRepository,
                                  PersonRepository personRepository, EntityManager entityManager) {
        this.userDataRepository = userDataRepository;
        this.personRepository = personRepository;
        this.entityManager = entityManager;
    }

    @Test
    @Transactional
    void deleteByUserDataIdAndNameFile_Test() {
        String email = "Test" + (int) Math.round(Math.random() * 500) + "@example.com";
        String password = "Password123";
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(password);

        personRepository.save(person);

        UserData userData = new UserData();
        userData.setNameFile("test" + (int) Math.round(Math.random() * 500) + ".txt");
        userData.setUserDataId(person.getId());
        userDataRepository.save(userData);

        // Удаление файла по имени и id пользователя
        userDataRepository.deleteByUserDataIdAndNameFile(userData.getUserDataId(), userData.getNameFile());
        entityManager.flush();

        // Проверка, что файл успешно удален
        UserData deletedEntity = userDataRepository.findByUserDataIdAndNameFile(userData.getUserDataId(), userData.getNameFile());
        assertNull(deletedEntity);


    }

    @Test
    void findAllByUserDataIdAndNameFileContaining_Test() {
        String email = "Test" + (int) Math.round(Math.random() * 500) + "@example.com";
        String password = "Password123";
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(password);

        personRepository.save(person);

        UserData userData = new UserData();
        userData.setNameFile("test" + (int) Math.round(Math.random() * 500) + ".txt");
        userData.setUserDataId(person.getId());
        userDataRepository.save(userData);

        List<UserData> userDataList = userDataRepository.findAllByUserDataIdAndNameFileContaining(person.getId(), userData.getNameFile());

        Assertions.assertTrue(userDataList.size() > 0);
    }

}
