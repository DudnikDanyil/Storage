package spring.Storage.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import spring.Storage.dto.FileUploadDTO;
import spring.Storage.dto.InfoPersonDTO;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.FileUploadException;
import spring.Storage.models.Person;
import spring.Storage.models.User_Data;
import spring.Storage.repositories.PersonRepository;
import spring.Storage.repositories.UserDataRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final PersonRepository personRepository;
    private final PersonService personService;

    private final UserDataRepository userDataRepository;
    private final ModelMapper modelMapper;

    private final ConfigService configService;

    @Value("${upload.path}") // ловим наш файл и записываем в переменную
    private String uploadPath;


    @Autowired
    public FileService(PersonRepository personRepository, PersonService personService,
                       UserDataRepository userDataRepository, ModelMapper modelMapper, ConfigService configService) {
        this.personRepository = personRepository;
        this.personService = personService;
        this.userDataRepository = userDataRepository;
        this.modelMapper = modelMapper;
        this.configService = configService;
    }

    // Возврат информации по клиенту в виде JSON по названию получаемого файла
    @Transactional
    public List<InfoPersonDTO> getInformationAndSaveFiles(HttpServletRequest request,
                                                        /*  MultipartFile[] fileFile , */
                                                          FileUploadDTO fileUploadDTO) throws FileUploadException,
                                                                                    AbsentPersonIdException {

        if (request.getCookies() != null) {

            Cookie[] cookies = request.getCookies();

            List<InfoPersonDTO> PersonInfo = new ArrayList<>();

            String userId = configService.decodingJWTToken(cookies);


            try {
                //               for (PersonDTO personDTO1 : personDTO) {
                for (MultipartFile file : fileUploadDTO.getFileFile()) {
                    String resultFileName = file.getOriginalFilename();

                    Person person = personRepository.findAllById(Integer.parseInt(userId));

                    File uploadDir = new File(uploadPath + "/" + person.getEmail());

                    if (!uploadDir.exists()) {
                        uploadDir.mkdir(); // если директории нет, создать ее
                    }

                    if (uploadDir.createNewFile()) {
                        try (FileOutputStream fos = new FileOutputStream(uploadDir)) {
                            fos.write(file.getBytes());
                        }
                    }
                    file.transferTo(new File(uploadDir + "/" + resultFileName));


                }


            } catch (Throwable e) {
                throw new FileUploadException("Error while uploading files!");
            }


            int i = 0;

            for (String nameFile : fileUploadDTO.getNameFile()) {
                String dataFile = fileUploadDTO.getDateFile()[i];
                String typeFile = fileUploadDTO.getTypeFile()[i];

                User_Data personToAdd = new User_Data();

                personToAdd.setNameFile(nameFile);
                personToAdd.setDateFile(dataFile);
                personToAdd.setTypeFile(typeFile);
                personToAdd.setUser_data_id(Integer.parseInt(userId));


                userDataRepository.save(personToAdd);


                                InfoPersonDTO personInf = configService.convertToInfoPersonDTO(personToAdd);

                                PersonInfo.add(personInf);


            }
            return PersonInfo;

        } else {
            InfoPersonDTO infoPersonDTO1 = new InfoPersonDTO();
            return configService.generatingPathObjectWithDataFalse(infoPersonDTO1);
        }


    }
}


