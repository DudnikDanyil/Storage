package spring.Storage.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import spring.Storage.dto.MyObject;
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
    public List<InfoPersonDTO> getInformationAndSaveFiles(/* HttpServletRequest request, */
            FileUploadDTO fileUploadDTO) throws FileUploadException,
            AbsentPersonIdException {

//        if (request.getCookies() != null) {

//            Cookie[] cookies = request.getCookies();

        List<InfoPersonDTO> PersonInfo = new ArrayList<>();

//            String userId = configService.decodingJWTToken(cookies);

        String userId = "339";

        saveFiles(fileUploadDTO, userId);

        int i = 0;
        int j = 0;
        int q = 0;

        for (String nameFile : fileUploadDTO.getNameFile()) {
            String dataFile = fileUploadDTO.getDateFile()[i++];
            String sizeFile = fileUploadDTO.getSizeFile()[j++];
            String typeFile = fileUploadDTO.getTypeFile()[q++];


            float megabytes = (float) Integer.parseInt(sizeFile) / 1024 / 1024;


            String sizeFailModified = "";

            if (megabytes < 1) {
                sizeFailModified = ">1";
            } else {

                sizeFailModified = String.valueOf((int) megabytes);

            }


            System.out.println(megabytes);
            UserData personToAdd = new UserData();

            personToAdd.setNameFile(nameFile);
            personToAdd.setDateFile(dataFile);
            personToAdd.setSizeFile(sizeFailModified);
            personToAdd.setTypeFile(typeFile);
            personToAdd.setUserDataId(Integer.parseInt(userId));

            if (userDataRepository.findByUserDataIdAndAndNameFile(Integer.parseInt(userId), nameFile).isEmpty()) {

                userDataRepository.save(personToAdd);

                InfoPersonDTO personInf = configService.convertToInfoPersonDTO(personToAdd);

                PersonInfo.add(personInf);
            } else {
                throw new FileUploadException("A file with this name already exists!");
            }

        }

        List<InfoPersonDTO> PersonInfoList = configService.listFillingInfoPersonDTO(PersonInfo);

        return PersonInfoList;

//        } else {
//            InfoPersonDTO infoPersonDTO1 = new InfoPersonDTO();
//            return configService.generatingPathObjectWithDataFalse(infoPersonDTO1);
//        }
    }

    private void saveFiles(FileUploadDTO fileUploadDTO, String userId) throws FileUploadException {

        try {
            for (MultipartFile file : fileUploadDTO.getFileFile()) {
                String resultFileName = file.getOriginalFilename();
                String lowercaseFileName = resultFileName.toLowerCase();

                Person person = personRepository.findAllById(Integer.parseInt(userId));

                File uploadDir = new File(uploadPath + "/" + person.getEmail());

                if (!uploadDir.exists()) {
                    uploadDir.mkdir(); // если директории нет, создать ее
                }


//                    if (uploadDir.createNewFile()) {
//                        try (FileOutputStream fos = new FileOutputStream(uploadDir)) {
//                            fos.write(file.getBytes());
//                        }
//                    }
//                    file.transferTo(new File(uploadDir + "/" + resultFileName));
                File resultFile = new File(uploadDir, lowercaseFileName);
                if (resultFile.exists()) {
                    throw new FileUploadException("Файл с именем " + lowercaseFileName + " уже существует");
                }

                file.transferTo(resultFile);
            }
        } catch (Throwable e) {
            throw new FileUploadException("Error while uploading files!");
        }
    }

    @Transactional
    public ResponseEntity<byte[]> downloadFileService(HttpServletRequest request) throws FileUploadException {

        try {

//            if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();

//                String userId = configService.decodingJWTToken(cookies);

            String userId = "339";

            Person person = personRepository.findAllById(Integer.parseInt(userId));

            String filePath = uploadPath + "/" + person.getEmail() + "/" + request.getParameter("nameFile");
            File file = new File(filePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "filename=" + file.getName());
            headers.setContentLength(file.length());

            Resource resource = new PathResource(uploadPath + "/" + person.getEmail() + "/" + request.getParameter("nameFile"));

            byte[] data = StreamUtils.copyToByteArray(resource.getInputStream());

            return new ResponseEntity<>(data, headers, HttpStatus.OK);
            //           }

//            throw new FileUploadException("No cookies!");

        } catch (Throwable e) {
            throw new FileUploadException("File upload error");
        }
    }

    @Transactional
    public FileStateDTO deletingFileService(HttpServletRequest request) throws AbsentPersonIdException, FileUploadException {

        FileStateDTO result = new FileStateDTO();

        //       if (request.getCookies() != null) {
        Cookie[] cookies = request.getCookies();

//            String userId = configService.decodingJWTToken(cookies);

        String userId = "339";

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
//        }
        //       throw new FileUploadException("No cookies!");
    }

    @Transactional
    public List<InfoPersonDTO> searchFileService(HttpServletRequest request) throws AbsentPersonIdException, FileUploadException {

//        if (request.getCookies() != null) {
        Cookie[] cookies = request.getCookies();

//            String userDataId = configService.decodingJWTToken(cookies);

        String userDataId = "339";

        List<UserData> personList = userDataRepository.findAllByUserDataIdAndNameFileStartingWith(Integer.parseInt(userDataId), request.getParameter("nameFile"));


        if (!personList.isEmpty()) {
            List<InfoPersonDTO> personListInfo = personList.stream().map(this::convertToInfoPersonDTO).collect(Collectors.toList());
            return configService.listFillingInfoPersonDTO(personListInfo);
        }
        throw new FileUploadException("File matching the search will be rejected");
        //           }


//        throw new AbsentPersonIdException("No cookies");
    }

    public List<InfoPersonDTO> editingFileName(HttpServletRequest request) throws FileUploadException, AbsentPersonIdException {


        //       if (request.getCookies() != null) {
        Cookie[] cookies = request.getCookies();

//            String userId = configService.decodingJWTToken(cookies);

        String userDataId = "339";

        Person person = personRepository.findAllById(Integer.parseInt(userDataId));

        String oldNameFile = request.getParameter("oldNameFile");

        UserData UserDataFindByNewNameFile = userDataRepository.findByUserDataIdAndNameFile(Integer.parseInt(userDataId), oldNameFile);

       String newVerifiedNameFile = validationEnteredNameFile(request.getParameter("newNameFile"), UserDataFindByNewNameFile);



        File oldFile = new File(uploadPath + "/" + person.getEmail() + "/" + oldNameFile);

        File newFile = new File(uploadPath + "/" + person.getEmail() + "/" + newVerifiedNameFile);


        if (oldFile.renameTo(newFile)) {

            List<UserData> userDataList = userDataRepository.findByUserDataIdAndAndNameFile(Integer.parseInt(userDataId), oldNameFile);

            for (UserData userData : userDataList) {
                userData.setNameFile(newVerifiedNameFile);
                userDataRepository.save(userData);
            }

            List<UserData> personList = userDataRepository.findAllByUserDataIdAndNameFileStartingWith(Integer.parseInt(userDataId), newVerifiedNameFile);

            List<InfoPersonDTO> personListInfo = personList.stream().map(this::convertToInfoPersonDTO).collect(Collectors.toList());
            return configService.listFillingInfoPersonDTO(personListInfo);

        } else {
            throw new FileUploadException("An error occurred while renaming the file!");
        }
//        }
//        throw new AbsentPersonIdException("No cookies");
    }
    
    private String validationEnteredNameFile(String newNameFile,
                                              UserData UserDataFindByNewNameFile){

        int extensionIndex = newNameFile.lastIndexOf(".");

        if (extensionIndex == -1) {

            newNameFile += UserDataFindByNewNameFile.getTypeFile();
        } else if (extensionIndex == newNameFile.length() - 1) {

            newNameFile = newNameFile.substring(0, extensionIndex) + UserDataFindByNewNameFile.getTypeFile();
        } else {

            newNameFile = newNameFile.substring(0, extensionIndex) + UserDataFindByNewNameFile.getTypeFile();
        }

        return newNameFile;
    }

    public InfoPersonDTO convertToInfoPersonDTO(UserData user_data) {
        return modelMapper.map(user_data, InfoPersonDTO.class);
    }
}



