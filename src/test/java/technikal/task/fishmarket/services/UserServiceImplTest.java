package technikal.task.fishmarket.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import technikal.task.fishmarket.models.User;
import technikal.task.fishmarket.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;


// Optional<User> getUserByLogin(String login)
    @Test
    void getUserByLogin_whenUserExists_thenReturnNonEmptyOptional() {
        // given
        var login = "user";
        var password = "password";
        var id = 1L;
        var expected = new User();
        expected.setId(id);
        expected.setLogin(login);
        expected.setPassword(password);

        // mockito
        Mockito.when(userRepository.findByLogin(login))
                .thenReturn(Optional.of(expected));

        // when
        Optional<User> userByLogin = userService.getUserByLogin(login);

        // then
        assertNotNull(userByLogin);
        assertTrue(userByLogin.isPresent());
        assertEquals(id, userByLogin.get().getId());
        assertEquals(login, userByLogin.get().getLogin());
        assertEquals(password, userByLogin.get().getPassword());
    }

    @Test
    void getUserByLogin_whenUserDoesntExist_thenReturnEmptyOptional() {
        // given
        var login = "user";

        // mockito
        Mockito.when(userRepository.findByLogin(login))
                .thenReturn(Optional.empty());

        // when
        Optional<User> userByLogin = userService.getUserByLogin(login);

        // then
        assertNotNull(userByLogin);
        assertFalse(userByLogin.isPresent());
    }

    @Test
    void getUserByLogin_whenParameterLoginIsNull_thenThrowException() {
        // given
        String login = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userService.getUserByLogin(login));
    }

}