package spring.Storage.models;

import javax.persistence.*;

@Entity
@Table(name = "User_Data")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int data_id;

    @Column(name = "user_data_id")
    private int userDataId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_data_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Person owner;

    @Column(name = "file_name")
    private String nameFile;

    @Column(name = "date_of_download_file")
    private String dateFile;

    @Column(name = "file_type")
    private String typeFile;

    public UserData() {

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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public int getUserDataId() {
        return userDataId;
    }

    public void setUserDataId(int userDataId) {
        this.userDataId = userDataId;
    }
}
