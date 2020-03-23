package ro.fortech.internship.vinylshop.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fortech.internship.vinylshop.role.model.Role;
import ro.fortech.internship.vinylshop.role.model.RoleType;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByType(RoleType roleType);
}
