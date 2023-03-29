package spring.Storage.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadDTO {

    int userId;
    private String[] nameFile;
    private String[] dateFile;
    private String[] sizeFile;

    private MultipartFile[] fileFile;

    public String[] getNameFile() {
        return nameFile;
    }

    public void setNameFile(String[] nameFile) {
        this.nameFile = nameFile;
    }

    public String[] getDateFile() {
        return dateFile;
    }

    public void setDateFile(String[] dateFile) {
        this.dateFile = dateFile;
    }

    public String[] getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(String[] sizeFile) {
        this.sizeFile = sizeFile;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public MultipartFile[] getFileFile() {
        return fileFile;
    }

    public void setFileFile(MultipartFile[] fileFile) {
        this.fileFile = fileFile;
    }
}
