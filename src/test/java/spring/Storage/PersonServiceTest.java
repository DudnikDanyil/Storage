package spring.Storage;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import spring.Storage.dto.PersonDTO;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    void findByUserNameAndPassword_Test(){
        PersonDTO personDTO = new PersonDTO();
        personDTO.setEmail("test@example.com");
        personDTO.setPassword("test_password");


    }

}
