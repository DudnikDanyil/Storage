package spring.Storage.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class PersonDTO {

    @NotEmpty(message = "Name must not be empty")
//    @Size(min=2, max=100, message = "Name must be 2 to 100 characters long")
//    @Email
    private String email;

//    @NotEmpty(message = "Password must not be empty")
 //   @Size(min=8, max=50, message = "Password must be 8 to 50 characters long")
    // Настраиваем паттер валидации
//    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}", message = "Incorrect password!")
    private String password;

//    @Size(max=50)
    private String nameFile;

    private String dateFile;

    private String sizeFile;

    private String typeFile;

    private MultipartFile[] fileFile;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getDateFile() {
        return dateFile;
    }

    public void setDateFile(String dateFile) {
        this.dateFile = dateFile;
    }

    public String getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(String sizeFile) {
        this.sizeFile = sizeFile;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    public MultipartFile[] getFileFile() {
        return fileFile;
    }

    public void setFileFile(MultipartFile[] fileFile) {
        this.fileFile = fileFile;
    }
}
