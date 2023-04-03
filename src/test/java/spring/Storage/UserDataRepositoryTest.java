package spring.Storage;

import javax.persistence.EntityManager;

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

//    @org.junit.jupiter.api.Test
//    @Transactional
//    void deleteByUserDataIdAndNameFile_Test(){
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
//        // Удаление файла по имени и id пользователя
//        userDataRepository.deleteByUserDataIdAndNameFile(userData.getUserDataId(), userData.getNameFile());
//        entityManager.flush(); // вызываем для применения изменений в БД
//
//        // Проверка, что файл успешно удален
//        UserData deletedEntity = userDataRepository.findByUserDataIdAndNameFile(userData.getUserDataId(), userData.getNameFile());
//        assertNull(deletedEntity);
//
//
//    }

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
//      List<UserData> userDataList = userDataRepository.findAllByUserDataIdAndNameFileStartingWith(person.getId(), userData.getNameFile());
//
//        assertThat(userDataList).hasSizeGreaterThan(0);
//    }


}
