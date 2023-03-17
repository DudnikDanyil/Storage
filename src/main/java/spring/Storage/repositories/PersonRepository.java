package spring.Storage.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import spring.Storage.models.Person;
import spring.Storage.models.UserData;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    Optional<Person> findByEmailAndPassword(String username, String password);

    Person findAllByEmail(String email);

    Optional<Person> findByEmail(String email);
    Person findPersonByEmail(String email);

    Optional<Person> findPersonById(int userId);

    Person findAllById(int userId);
}
