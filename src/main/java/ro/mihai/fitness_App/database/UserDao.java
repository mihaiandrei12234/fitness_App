package ro.mihai.fitness_App.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.mihai.fitness_App.database.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    User findByEmail(String email);

}
