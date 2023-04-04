package spring.Storage.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.Storage.dto.InfoPersonDTO;
import spring.Storage.models.UserData;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ConfigService {

    private final ModelMapper modelMapper;

    @Autowired
    public ConfigService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public List<InfoPersonDTO> generatingPathObjectWithDataFalse(InfoPersonDTO infoPersonDTO) {
        infoPersonDTO.setData("false");
        List<InfoPersonDTO> inf = new ArrayList<>();
        inf.add(infoPersonDTO);

        return inf;
    }

    public String decodingJWTToken(Cookie[] cookies) {

        String UserIdCookie = "";

        for (Cookie c : cookies) {

            UserIdCookie = c.getValue();
        }

        System.out.println(UserIdCookie);

        DecodedJWT decodedJWT;

        Algorithm algorithm = Algorithm.HMAC256("ACB");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        decodedJWT = verifier.verify(UserIdCookie);

        Claim claim = decodedJWT.getClaim("userId");
        String userId = claim.asString();

        System.out.println(userId);

        return userId;
    }

    public List<InfoPersonDTO> listFillingInfoPersonDTO(List<InfoPersonDTO> PersonListInf) {

        List<InfoPersonDTO> PersonListInf2 = new ArrayList<>();
        for (InfoPersonDTO personInf : PersonListInf) {
            personInf.setData("true");
            PersonListInf2.add(personInf);
        }

        return PersonListInf2;
    }

    public InfoPersonDTO convertToInfoPersonDTO(UserData user_data) {

        return modelMapper.map(user_data, InfoPersonDTO.class);
    }

}
