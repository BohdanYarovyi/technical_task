package technikal.task.fishmarket.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import technikal.task.fishmarket.services.UserService;
import technikal.task.fishmarket.models.Role;

import java.util.List;

@Component
public class DaoUserDetailService implements UserDetailsService {
    private final UserService userService;

    public DaoUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        technikal.task.fishmarket.models.User user = userService
                .getUserByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with username " + username));

        String login = user.getLogin();
        String password = user.getPassword();
        List<GrantedAuthority> grantedAuthority = getGrantedAuthority(user.getRole());

        return new User(login, password, grantedAuthority);
    }

    // now it is only one authority for user
    private List<GrantedAuthority> getGrantedAuthority(Role role) {
        String formattedRoleName = SecurityConfig.SPRING_GRANTED_AUTHORITY_PREFIX + role.getName();
        GrantedAuthority authority = new SimpleGrantedAuthority(formattedRoleName);

        return List.of(authority);
    }

}
