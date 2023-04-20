package spring.Storage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Storage.dto.FileStateDTO;
import spring.Storage.dto.FileUploadDTO;
import spring.Storage.dto.InfoPersonDTO;
import spring.Storage.dto.MyObject;
import spring.Storage.exception.AbsentPersonIdException;
import spring.Storage.exception.FileUploadException;
import spring.Storage.services.FileService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileController {

    public final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @DeleteMapping("/deleteFile")
    public FileStateDTO deleteFile(HttpServletRequest request) throws AbsentPersonIdException, FileUploadException {

        return fileService.deletingFileService(request);
    }

    @GetMapping(value = "/download")
    @ResponseBody
    public ResponseEntity<byte[]> downloadFile(HttpServletRequest request) throws FileUploadException {

        return fileService.downloadFileService(request);
    }

    @PutMapping(value = "/editing")
    @ResponseBody
    public List<InfoPersonDTO> editingFile(HttpServletRequest request) throws FileUploadException, AbsentPersonIdException {

        return fileService.editingFileName(request);
    }

    @ResponseBody
    @GetMapping("/search")
    public List<InfoPersonDTO> searchFile(HttpServletRequest request) throws AbsentPersonIdException, FileUploadException {

        return fileService.searchFileService(request);
    }

    @PostMapping("/loading")
    @ResponseBody
    public ResponseEntity<List<InfoPersonDTO>> fileUpload( HttpServletRequest request,
            @ModelAttribute FileUploadDTO fileUploadDTO) throws FileUploadException, AbsentPersonIdException {

        return ResponseEntity.ok(fileService.getInformationAndSaveFiles(request,  fileUploadDTO));
    }
}
