package spring.Storage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Storage.dto.MyObject;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.FileUploadException;
import spring.Storage.services.FileService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class FileEditingController {

    private final FileService fileService;

    @Autowired
    public FileEditingController(FileService fileService) {
        this.fileService = fileService;
    }

    @PutMapping(value = "/editing")
    @ResponseBody
    public List<MyObject> editingFile(HttpServletRequest request) throws FileUploadException, AbsentPersonIdException {

        return fileService.editingFileName(request);
    }

}
