package ro.mihai.fitness_App.database;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.mihai.fitness_App.database.User;
import ro.mihai.fitness_App.database.UserDao;
import ro.mihai.fitness_App.pop.up.msg.InvalidEmail;
import ro.mihai.fitness_App.pop.up.msg.InvalidPassword;

import javax.persistence.ElementCollection;
import java.util.ArrayList;


@Service
public class UserService {

    @Autowired
    UserDao userDao;


    public void save(String email, String password, String name, int chosenOption) throws InvalidEmail, InvalidPassword {

        if (password.length() < 10) {
            throw new InvalidPassword("Parola trebuie sa contina cel putin 10 caractere");
        }
        if (users.contains(email)) {
            throw new InvalidEmail("Exista deja un utilizator cu acest email");
        }
        String passwordMd5 = DigestUtils.md5Hex(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordMd5);
        user.setName(name);
        user.setChosenOption(chosenOption);
        userDao.save(user);
        users.add(email);

    }


    @ElementCollection
    ArrayList<String> users = new ArrayList<String>();

}
