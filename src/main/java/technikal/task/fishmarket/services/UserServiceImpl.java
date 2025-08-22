package technikal.task.fishmarket.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technikal.task.fishmarket.models.User;
import technikal.task.fishmarket.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Parameter 'login' is null");
        }

        return userRepository.findByLogin(login);
    }

}
