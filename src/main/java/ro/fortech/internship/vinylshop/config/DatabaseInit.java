package ro.fortech.internship.vinylshop.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.role.model.Role;
import ro.fortech.internship.vinylshop.role.model.RoleType;
import ro.fortech.internship.vinylshop.role.repository.RoleRepository;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInit {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createRoles() {
        Role customerRole = Role.builder().type(RoleType.CUSTOMER).build();
        Role managerRole = Role.builder().type(RoleType.MANAGER).build();
        Role adminRole = Role.builder().type(RoleType.ADMIN).build();
        roleRepository.save(customerRole);
        roleRepository.save(managerRole);
        roleRepository.save(adminRole);
        User customer = User.builder().email("user@user").cart(new Cart()).firstName("User").lastName("User")
                .password(passwordEncoder.encode("userS1mplu!")).role(customerRole).build();
        userRepository.save(customer);
        User manager = User.builder().email("manager@manager").cart(new Cart()).firstName("Manager").lastName("Manager")
                .password(passwordEncoder.encode("managerS1mplu!")).role(managerRole).build();
        userRepository.save(manager);
        User admin = User.builder().email("admin@admin").cart(new Cart()).firstName("Admin").lastName("Admin")
                .password(passwordEncoder.encode("adminS1mplu!")).role(adminRole).build();
        userRepository.save(admin);
    }
}
