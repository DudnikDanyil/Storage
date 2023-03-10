package spring.Storage.dto;


import org.springframework.web.multipart.MultipartFile;

public class JoinInfoDTO {

//    private Cookie[] cookie;
    private String email;
    private String password;
    private String nameFile;
    private String dateFile;
    private String typeFile;
    private MultipartFile[] fileFile;

//    public Cookie getCookie() {
//        return cookie;
//    }
//
//    public void setCookie(Cookie cookie) {
//        this.cookie = cookie;
//    }


//    public Cookie[] getCookie() {
//        return cookie;
//    }
//
//    public void setCookie(Cookie[] cookie) {
//        this.cookie = cookie;
//    }

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
