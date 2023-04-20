package spring.Storage.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.Storage.models.UserData;


import java.util.List;

public interface UserDataRepository extends CrudRepository<UserData, Integer> {
   void deleteByUserDataIdAndNameFile(int userId, String nameFile);

   List<UserData> findByUserDataIdAndAndNameFile(int userId, String nameFile);
   UserData findByUserDataIdAndNameFile(int userId, String oldNameFile);
   List<UserData> findAllByUserDataIdAndNameFileContaining(int userDataId, String nameFile);
}
