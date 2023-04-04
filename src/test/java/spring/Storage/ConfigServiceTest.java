package spring.Storage;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import spring.Storage.dto.InfoPersonDTO;
import spring.Storage.models.UserData;
import spring.Storage.services.ConfigService;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class ConfigServiceTest {

    private final ConfigService configService;

    @Autowired
    public ConfigServiceTest(ConfigService configService) {
        this.configService = configService;
    }

    @Test
    void decodingJWTToken_Test(){

        String expectedUserId = String.valueOf((int) Math.round(Math.random() * 500));

        Algorithm algorithm = Algorithm.HMAC256("ACB");
        String token = JWT.create()
                .withClaim("userId", expectedUserId)
                .sign(algorithm);

        Cookie[] cookies = new Cookie[] { new Cookie("UserIdCookie", token) };

        String actualUserId = configService.decodingJWTToken(cookies);

        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    void convertToInfoPersonDTO_Test(){
        UserData personToAdd = new UserData();

        personToAdd.setNameFile("New file.txt");
        personToAdd.setDateFile("12.03.2022");
        personToAdd.setSizeFile("12");
        personToAdd.setTypeFile(".txt");

        InfoPersonDTO infoPersonDTO = configService.convertToInfoPersonDTO(personToAdd);

        assertEquals(infoPersonDTO.getNameFile(), personToAdd.getNameFile());
        assertEquals(infoPersonDTO.getDateFile(), personToAdd.getDateFile());
        assertEquals(infoPersonDTO.getTypeFile(), personToAdd.getTypeFile());
        assertEquals(infoPersonDTO.getTypeFile(), personToAdd.getTypeFile());
    }


}
