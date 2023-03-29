package spring.Storage.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import spring.Storage.models.Person;
import spring.Storage.models.UserData;


import java.util.List;

public interface UserDataRepository extends CrudRepository<UserData, Integer> {
   void deleteByUserDataIdAndNameFile(int userId, String nameFile);

   List<UserData> findByUserDataIdAndAndNameFile(int userId, String nameFile);
   List<UserData> findAllByUserDataIdAndNameFileStartingWith(int userDataId, String nameFile);
}
