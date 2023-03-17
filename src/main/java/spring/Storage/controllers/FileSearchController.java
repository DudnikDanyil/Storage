package spring.Storage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Storage.dto.InfoPersonDTO;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.FileUploadException;
import spring.Storage.models.Person;
import spring.Storage.services.FileService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FileSearchController {

    private final FileService fileService;

    @Autowired
    public FileSearchController(FileService fileService) {
        this.fileService = fileService;
    }

    @ResponseBody
    @GetMapping("/search")
    public List<InfoPersonDTO> searchFile(HttpServletRequest request) throws AbsentPersonIdException, FileUploadException {

        return fileService.searchFileService(request);
    }
}
