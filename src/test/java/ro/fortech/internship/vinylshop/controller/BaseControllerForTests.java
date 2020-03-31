package ro.fortech.internship.vinylshop.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.fortech.internship.vinylshop.role.model.Role;
import ro.fortech.internship.vinylshop.role.model.RoleType;
import ro.fortech.internship.vinylshop.role.repository.RoleRepository;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerForTests {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected final TestRestTemplate restTemplate = new TestRestTemplate();

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    public void saveUserInDb(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role roleCustomer = roleRepository.findByType(RoleType.CUSTOMER);
        user.setRole(roleCustomer);
        userRepository.save(user);
    }

    protected String createUrl(String path) {
        return "http://localhost:" + port + path;
    }

}
