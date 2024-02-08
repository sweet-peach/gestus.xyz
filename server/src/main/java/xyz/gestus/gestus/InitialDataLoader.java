package xyz.gestus.gestus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.gestus.gestus.models.UserModel;
import xyz.gestus.gestus.repositories.UserRepository;

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
        Optional<UserModel> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            UserModel newUser = new UserModel();
            newUser.setEmail(email);
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setRole("ADMIN");
            userRepository.save(newUser);
        }
    }
}

