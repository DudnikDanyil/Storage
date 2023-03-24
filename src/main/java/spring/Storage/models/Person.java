package spring.Storage.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Person")
@SecondaryTable(name = "User_Data", pkJoinColumns =
@PrimaryKeyJoinColumn(name = "user_data_id", referencedColumnName = "id"))
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserData> information;
    private String email;

    private String password;

    @Column(name = "file_name", table = "User_Data")
    private String nameFile;

    @Column(name = "date_of_download_file", table = "User_Data")
    private String dateFile;

    @Column(name = "file_type", table = "User_Data")
    private String typeFile;

   public Person() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public List<UserData> getInformation() {
        return information;
    }

    public void setInformation(List<UserData> information) {
        this.information = information;
    }


}
