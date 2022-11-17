package ru.netology.springbootrest.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.springbootrest.exception.InvalidCredentials;
import ru.netology.springbootrest.exception.UnauthorizedUser;
import ru.netology.springbootrest.model.Authorities;
import ru.netology.springbootrest.model.User;
import ru.netology.springbootrest.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorizationService {

    UserRepository userRepository;


    public List<Authorities> getAuthorities(User user) {
        if (isEmpty(user.getLogin()) || isEmpty(user.getPassword())) {
            throw new InvalidCredentials("User name or password is empty");
        }
        List<Authorities> userAuthorities = userRepository.getUserAuthorities(user.getLogin(), user.getPassword());
        if (isEmpty(userAuthorities)) {
            throw new UnauthorizedUser("Unknown user " + user.getLogin());
        }
        return userAuthorities;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(List<?> str) {
        return str == null || str.isEmpty();
    }
}
