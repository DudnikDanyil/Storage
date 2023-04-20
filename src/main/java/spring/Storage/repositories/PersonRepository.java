package spring.Storage.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.Storage.models.Person;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    Optional<Person> findByEmailAndPassword(String username, String password);

    Person findAllByEmail(String email);

    Optional<Person> findByEmail(String email);
    Person findPersonByEmail(String email);

    Optional<Person> findPersonById(int userId);

    Person findAllById(int userId);
}
