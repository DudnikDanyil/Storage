package spring.Storage.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.Storage.models.User_Data;

public interface UserDataRepository extends CrudRepository<User_Data, Integer> {

}
