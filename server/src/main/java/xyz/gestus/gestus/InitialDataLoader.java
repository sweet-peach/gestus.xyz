package xyz.gestus.gestus;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.gestus.gestus.core.user.Role;
import xyz.gestus.gestus.core.user.User;
import xyz.gestus.gestus.core.user.UserRepository;

import java.util.Optional;

@Component
public class InitialDataLoader {

    private boolean alreadySetup = false;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public InitialDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        createAdminUserIfNotFound("admin@example.com", "admin");

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

