package spring.Storage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.Storage.dto.FileStateDTO;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.FileUploadException;
import spring.Storage.services.FileService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DeletingFileController {

   public final FileService fileService;

   @Autowired
    public DeletingFileController(FileService fileService) {
        this.fileService = fileService;
    }


    @DeleteMapping("/deleteFile")
    public FileStateDTO deleteFile(HttpServletRequest request) throws AbsentPersonIdException, FileUploadException {

        return fileService.deletingFileService(request);
    }
}
