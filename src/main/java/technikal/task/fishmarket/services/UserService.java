package technikal.task.fishmarket.services;

import technikal.task.fishmarket.models.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByLogin(String login);

}
