# Задача Сервис авторизации

## Описание
Реализовал сервис авторизации пользователей по логину и паролю. 

Для работы выполнил необходимые шаги:

0. Создал spring boot приложение и все классы: контроллер, сервис и репозиторий. 

1. Запрос на разрешения приходит на контроллер:

```java
@RestController
public class AuthorizationController {
    AuthorizationService service;
    
    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@RequestParam("user") String user, @RequestParam("password") String password) {
        return service.getAuthorities(user, password);
    }
}
``` 

2. Класс-сервис, который обрабатывает введенные логин и пароль, выглядит следующим образом. 

```java
public class AuthorizationService {
    UserRepository userRepository;

    List<Authorities> getAuthorities(String user, String password) {
        if (isEmpty(user) || isEmpty(password)) {
            throw new InvalidCredentials("User name or password is empty");
        }
        List<Authorities> userAuthorities = userRepository.getUserAuthorities(user, password);
        if (isEmpty(userAuthorities)) {
            throw new UnauthorizedUser("Unknown user " + user);
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
``` 
Он принимает в себя логин и пароль и возвращает разрешения для этого пользователя, если такой пользователь найден и данные валидны. 
Если присланные данные неверны, тогда выкидывается InvalidCredentials:

```java
public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials(String msg) {
        super(msg);
    }
}
``` 

Если репозиторий не вернул никаких разрешений, либо вернул пустую коллекцию, тогда выкидывается ошибка UnauthorizedUser:

```java
public class UnauthorizedUser extends RuntimeException {
    public UnauthorizedUser(String msg) {
        super(msg);
    }
}
``` 

Enum с разрешениями выглядит следующим образом:

```java
public enum Authorities {
    READ, WRITE, DELETE
}
``` 

3. Код метод getUserAuthorities класс UserRepository, который возвращает либо разрешения, либо пустой массив.

```java
public class UserRepository {
    public List<Authorities> getUserAuthorities(String user, String password) {
       for (User user : authorizedUsers) {
          if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
             return Arrays.asList(Authorities.values());
          }
       }
        return new ArrayList<>();
    }
}
``` 

4. Теперь, когда весь код готов, написал обработчики ошибок, которые выкидывает сервис `AuthorizationService`.
     - На `InvalidCredentials` он отсылает обратно клиенту  http статус с кодом 400 и телом в виде сообщения из exception'а
     - На `UnauthorizedUser` он отсылает обратно клиенту http статус с кодом 401 и телом в виде сообщения из exception'а и писать в консоль сообщение из exception'а

# Задача Прокси на nginx

## Описание
Реализуем нашего первое приложение с обратным прокси перед ним. Напишем конфигурацию nginx, который будет возвращать статический сайт, с помощью которого можно обратиться к нашему сервису авторизации из прошлого домашнего задания.

1. Первым делом нужно создать html форму для авторизации, которую нам будет возвращать nginx. Этот файл нужно положить в соответствующую папку, откуда nginx сможет ее забрать.

```html
<html>
    <body>
        <h1>Sign in form</h1>
    
        <form action="/authorize" method="get" target="_blank">
          <label for="user">User name:</label>
          <input type="text" id="user" name="user"><br><br>
          <label for="password">Password:</label>
          <input type="text" id="password" name="password"><br><br>
          <button type="submit">Submit</button>
        </form>
    </body>
</html>
```

2. Вам необходимо написать конфигурацию для nginx так, чтобы он при вызове http://localhost/signin возвращал нам эту html страницу, а все остальное он проксировал на наше spring boot приложение, которое работает на порту 8080.
То, что вы напишите в конфигурации, добавьте в текстовый файл(формат файла любой, например, txt) в ваш проект с сервисом авторизации, запушьте в ваш репозиторий, и пришлите ссылку на репозиторий.