package spring.Storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import spring.Storage.models.Person;
import spring.Storage.repositories.PersonRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class PersonRepositoryTest {

    private final PersonRepository personRepository;

    @Autowired
    public PersonRepositoryTest(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Test
     void findByEmailAndPassword_Test(){
        // Создаем тестовые данные
        String email = "Test" + (int) Math.round(Math.random() * 500) + "@example.com";
        String password = "Password123";
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(password);

        // Создаем репозиторий и сохраняем тестовые данные
        personRepository.save(person);

        // Ищем Person по email и password
        Optional<Person> foundPerson = personRepository.findByEmailAndPassword(email, password);

        // Проверяем, что Person найден и имеет правильные данные
        assertTrue(foundPerson.isPresent());
        assertEquals(person.getEmail(), foundPerson.get().getEmail());
        assertEquals(person.getPassword(), foundPerson.get().getPassword());
    }

    @Test
    void findAllByEmail_Test(){
        // Создаем тестовые данные
        String email = "Test" + (int) Math.round(Math.random() * 500) + "@example.com";
        String password = "Password123";
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(password);

        // Создаем репозиторий и сохраняем тестовые данные
        personRepository.save(person);

        // Ищем Person по email и password
        Person foundPerson = personRepository.findAllByEmail(email);

        // Проверяем, что Person найден и имеет правильные данные
        assertTrue(foundPerson != null);
        assertEquals(person.getEmail(), foundPerson.getEmail());
    }

    @Test
    void findByEmail_Test(){
        // Создаем тестовые данные
        String email = "Test" + (int) Math.round(Math.random() * 500) + "@example.com";
        String password = "Password123";
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(password);

        // Создаем репозиторий и сохраняем тестовые данные
        personRepository.save(person);

        // Ищем Person по email и password
        Optional<Person> foundPerson = personRepository.findByEmail(email);

        // Проверяем, что Person найден и имеет правильные данные
        assertTrue(foundPerson.isPresent());
        assertEquals(person.getEmail(), foundPerson.get().getEmail());
    }

    @Test
    void findPersonByEmail_Test(){
        // Создаем тестовые данные
        String email = "Test" + (int) Math.round(Math.random() * 500) + "@example.com";
        String password = "Password123";
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(password);

        // Создаем репозиторий и сохраняем тестовые данные
        personRepository.save(person);

        // Ищем Person по email и password
        Person foundPerson = personRepository.findPersonByEmail(email);

        // Проверяем, что Person найден и имеет правильные данные
        assertTrue(foundPerson != null);
        assertTrue(foundPerson.getNameFile() == null);
        assertEquals(person.getEmail(), foundPerson.getEmail());
    }

    @Test
    void findPersonById_Test(){
        // Создаем тестовые данные
        String email = "Test" + (int) Math.round(Math.random() * 500) + "@example.com";
        String password = "Password123";
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(password);

        // Создаем репозиторий и сохраняем тестовые данные
        personRepository.save(person);

        // Ищем Person по email и password
        Optional<Person> foundPerson = personRepository.findPersonById(person.getId());

        // Проверяем, что Person найден и имеет правильные данные
        assertTrue(foundPerson.isPresent());
        assertEquals(person.getId(), foundPerson.get().getId());
    }

    @Test
    void findAllById_Test(){
        // Создаем тестовые данные
        String email = "Test" + (int) Math.round(Math.random() * 500) + "@example.com";
        String password = "Password123";
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(password);

        // Создаем репозиторий и сохраняем тестовые данные
        personRepository.save(person);

        // Ищем Person по email и password
        Person foundPerson = personRepository.findAllById(person.getId());

        // Проверяем, что Person найден и имеет правильные данные
        assertTrue(foundPerson != null);
        assertEquals(person.getId(), foundPerson.getId());
    }

}
