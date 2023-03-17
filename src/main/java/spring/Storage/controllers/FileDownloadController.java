package spring.Storage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.FileUploadException;
import spring.Storage.services.FileService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileDownloadController {

    private final FileService fileService;

    @Autowired
    public FileDownloadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/download")
    @ResponseBody
    public ResponseEntity<byte[]> downloadFile(HttpServletRequest request) throws AbsentPersonIdException, IOException, FileUploadException {

        return fileService.downloadFileService(request);
    }
}
