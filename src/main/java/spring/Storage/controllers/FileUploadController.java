package spring.Storage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Storage.dto.FileUploadDTO;
import spring.Storage.dto.InfoPersonDTO;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.FileUploadException;
import spring.Storage.repositories.PersonRepository;
import spring.Storage.services.FileService;
import spring.Storage.services.PersonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class FileUploadController {
    private final PersonService personService;
    private final FileService fileService;
    private final PersonRepository personRepository;

    @Autowired
    public FileUploadController(PersonService personService, FileService fileService,
                                PersonRepository personRepository) {
        this.personService = personService;
        this.fileService = fileService;
        this.personRepository = personRepository;
    }

    @PostMapping("/loading")
    @ResponseBody
    public ResponseEntity<List<InfoPersonDTO>> fileUpload(HttpServletRequest request,
                                                        /*  @RequestParam("fileFile") MultipartFile[] fileFile, */
                                                          @ModelAttribute FileUploadDTO fileUploadDTO) throws FileUploadException, AbsentPersonIdException {
//        MultipartFile[] files = personDTO.getFileFile();
//        System.out.println(personDTO.getNameFile());
//        for(String nameFail : fileUploadDTO.getNameFile()){
//            System.out.println(nameFail);
//        }

//        System.out.println(Arrays.stream(fileUploadDTO.getFileFile()).iterator());
            return ResponseEntity.ok(fileService.getInformationAndSaveFiles(request, fileUploadDTO));
//        String inf = personDTO;
//        return null;
    }


}


