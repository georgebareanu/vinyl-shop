package ro.fortech.internship.vinylshop.user.repository;

import org.springframework.data.repository.CrudRepository;
import ro.fortech.internship.vinylshop.user.model.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
}
