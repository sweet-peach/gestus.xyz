package xyz.gestus.gestus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.gestus.gestus.feature.user.Role;
import xyz.gestus.gestus.feature.user.User;
import xyz.gestus.gestus.feature.user.UserRepository;

import java.util.Optional;

@Component
public class InitialDataLoader {

    private boolean alreadySetup = false;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${ROOT_USER_EMAIL}")
    private String rootUserEmail;

    @Value("${ROOT_USER_PASSWORD}")
    private String rootUserPassword;

    public InitialDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        createAdminUserIfNotFound(rootUserEmail, rootUserPassword);

        alreadySetup = true;
    }

    private void createAdminUserIfNotFound(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setRole(Role.ADMIN);
            userRepository.save(newUser);
        }
    }
}

