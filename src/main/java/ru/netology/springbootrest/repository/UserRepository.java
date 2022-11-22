package ru.netology.springbootrest.repository;

import org.springframework.stereotype.Repository;
import ru.netology.springbootrest.model.Authorities;
import ru.netology.springbootrest.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {

    private final List<User> authorizedUsers = new ArrayList<>();

    {
        authorizedUsers.add(new User("Vlad", "123321"));
        authorizedUsers.add(new User("Artem", "111222"));
        authorizedUsers.add(new User("Andrey", "222333"));
    }

    public List<Authorities> getUserAuthorities(String login, String password) {
        for (User user : authorizedUsers) {
            if (user.login().equals(login) && user.password().equals(password)) {
                return Arrays.asList(Authorities.values());
            }
        }
        return new ArrayList<>();
    }
}