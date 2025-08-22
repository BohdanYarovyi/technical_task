package technikal.task.fishmarket.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public static final String SPRING_GRANTED_AUTHORITY_PREFIX = "ROLE_";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        // access settings
        security.authorizeHttpRequests(settings -> settings
                .requestMatchers(HttpMethod.GET,"/fish").authenticated()
                .requestMatchers("/fish/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        // form login
        security.formLogin(Customizer.withDefaults());

        // cors
        security.cors(AbstractHttpConfigurer::disable);

        // csrf
        security.csrf(AbstractHttpConfigurer::disable);

        return security.build();
    }

}
