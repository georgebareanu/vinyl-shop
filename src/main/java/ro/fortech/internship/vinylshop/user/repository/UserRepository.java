package ro.fortech.internship.vinylshop.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fortech.internship.vinylshop.user.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
