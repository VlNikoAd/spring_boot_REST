package ru.netology.springbootrest.repository;

import org.springframework.stereotype.Repository;
import ru.netology.springbootrest.model.Authorities;
import ru.netology.springbootrest.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {

    List<User> userRepository = new ArrayList<>();

    {
        userRepository.add(new User("Vlad", "123321"));
        userRepository.add(new User("Artem", "111222"));
        userRepository.add(new User("Andrey", "222333"));
    }

    public List<Authorities> getUserAuthorities(String login, String password) {
        for (User user : userRepository) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return Arrays.asList(Authorities.values());
            }
        }
        return new ArrayList<>();
    }
}