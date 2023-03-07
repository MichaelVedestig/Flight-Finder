package pgp.flightfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pgp.flightfinder.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
