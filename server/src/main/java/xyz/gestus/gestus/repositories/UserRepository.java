package xyz.gestus.gestus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.gestus.gestus.models.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByEmail(String email);
}
