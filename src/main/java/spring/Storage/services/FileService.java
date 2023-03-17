package spring.Storage.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import spring.Storage.dto.FileStateDTO;
import spring.Storage.dto.FileUploadDTO;
import spring.Storage.dto.InfoPersonDTO;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.FileUploadException;
import spring.Storage.models.Person;
import spring.Storage.models.UserData;
import spring.Storage.repositories.PersonRepository;
import spring.Storage.repositories.UserDataRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final PersonRepository personRepository;

    private final UserDataRepository userDataRepository;

    private final ConfigService configService;

    private final ModelMapper modelMapper;

    @Value("${upload.path}") // ловим наш файл и записываем в переменную
    private String uploadPath;


    @Autowired
    public FileService(PersonRepository personRepository,
                       UserDataRepository userDataRepository,
                       ConfigService configService, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.userDataRepository = userDataRepository;
        this.configService = configService;
        this.modelMapper = modelMapper;
    }

    // Возврат информации по клиенту в виде JSON по названию получаемого файла
    @Transactional
    public List<InfoPersonDTO> getInformationAndSaveFiles(HttpServletRequest request,
                                                          FileUploadDTO fileUploadDTO) throws FileUploadException,
            AbsentPersonIdException {

        if (request.getCookies() != null) {

            Cookie[] cookies = request.getCookies();

            List<InfoPersonDTO> PersonInfo = new ArrayList<>();

            String userId = configService.decodingJWTToken(cookies);

            try {
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

                UserData personToAdd = new UserData();

                personToAdd.setNameFile(nameFile);
                personToAdd.setDateFile(dataFile);
                personToAdd.setTypeFile(typeFile);
                personToAdd.setUserDataId(Integer.parseInt(userId));

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




    @Transactional
    public ResponseEntity<byte[]> downloadFileService(HttpServletRequest request) throws AbsentPersonIdException, IOException, FileUploadException {

        try {

            if (request.getCookies() != null) {
                Cookie[] cookies = request.getCookies();

                String userId = configService.decodingJWTToken(cookies);

                Person person = personRepository.findAllById(Integer.parseInt(userId));

//            String filePath = "G:/Project/Spring/Joint project/Project_2/Storage-back_2_1/src/main/resources/img/123456@123456/Шаблон.txt";
                String filePath = uploadPath + "/" + person.getEmail() + "/" + request.getParameter("nameFile");
                File file = new File(filePath);

//                User_Data fileInfo = userDataRepository.findByUser_data_idAndNameFile(request.getParameter("nameFile"));

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "filename=" + file.getName());
                headers.setContentLength(file.length());

                Resource resource = new PathResource(uploadPath + "/" + person.getEmail() + "/" + request.getParameter("nameFile"));

                byte[] data = StreamUtils.copyToByteArray(resource.getInputStream());

                return new ResponseEntity<>(data, headers, HttpStatus.OK);
            }

            throw new FileUploadException("No cookies!");

        } catch (Throwable e) {
            throw new FileUploadException("File upload error");
        }
    }

    @Transactional
    public FileStateDTO deletingFileService(HttpServletRequest request) throws AbsentPersonIdException, FileUploadException {

        FileStateDTO result = new FileStateDTO();

        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();

            String userId = configService.decodingJWTToken(cookies);

            Person person = personRepository.findAllById(Integer.parseInt(userId));

            File file = new File(uploadPath + "/" + person.getEmail() + "/" + request.getParameter("nameFile"));


            if (file.exists()) {
                if (file.delete()) {

                    userDataRepository.deleteByUserDataIdAndNameFile(Integer.parseInt(userId), request.getParameter("nameFile"));

                    result.setFileIsDeleted(true);
                    return result;
                }
                throw new FileUploadException("The file does not exist!");
            }
            throw new FileUploadException("An error occurred while deleting a file!");
        }
        throw new FileUploadException("No cookies!");
    }

    @Transactional
    public List<InfoPersonDTO> searchFileService(HttpServletRequest request) throws AbsentPersonIdException, FileUploadException {

        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();

            String userDataId = configService.decodingJWTToken(cookies);

            List<UserData> personList = userDataRepository.findAllByUserDataIdAndNameFileStartingWith(Integer.parseInt(userDataId),  request.getParameter("nameFile"));

            if(!personList.isEmpty()) {
                return personList.stream().map(this::convertToInfoPersonDTO).collect(Collectors.toList());
            }
            throw new FileUploadException("File matching the search will be rejected");
            }

        throw new AbsentPersonIdException("No cookies");
    }


    public InfoPersonDTO convertToInfoPersonDTO(UserData user_data) {
        return modelMapper.map(user_data, InfoPersonDTO.class);
    }
}



