package ro.fortech.internship.vinylshop.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.role.model.Role;
import ro.fortech.internship.vinylshop.role.model.RoleType;
import ro.fortech.internship.vinylshop.role.repository.RoleRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInit {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void createRoles() {
        Role customerRole = Role.builder().type(RoleType.CUSTOMER).build();
        Role managerRole = Role.builder().type(RoleType.MANAGER).build();
        Role adminRole = Role.builder().type(RoleType.ADMIN).build();
        roleRepository.save(customerRole);
        roleRepository.save(managerRole);
        roleRepository.save(adminRole);
    }
}
