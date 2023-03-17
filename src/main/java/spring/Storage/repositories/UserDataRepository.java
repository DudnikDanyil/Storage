package spring.Storage.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.Storage.models.User_Data;

public interface UserDataRepository extends CrudRepository<User_Data, Integer> {

//    User_Data findByUser_data_idAndNameFile(String nameFail);

   void deleteByUserDataIdAndNameFile(int userId, String nameFile);
}
