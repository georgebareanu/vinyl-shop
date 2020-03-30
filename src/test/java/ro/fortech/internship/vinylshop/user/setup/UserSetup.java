package ro.fortech.internship.vinylshop.user.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.common.exception.ResourceNotFoundException;
import ro.fortech.internship.vinylshop.role.model.Role;
import ro.fortech.internship.vinylshop.role.model.RoleType;
import ro.fortech.internship.vinylshop.role.repository.RoleRepository;
import ro.fortech.internship.vinylshop.user.dto.AuthenticationTokenDto;
import ro.fortech.internship.vinylshop.user.dto.CreateUserDto;
import ro.fortech.internship.vinylshop.user.dto.LoginUserDto;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;
import ro.fortech.internship.vinylshop.user.service.UserService;

@Component
public class UserSetup {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    public void deleteUsersDatabase() {
        userRepository.deleteAll();
    }

    public User userFromRepositoryFindByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("NotFound"));
    }

    public CreateUserDto createValidUserDto() {
        return new CreateUserDto("John", "Pierce", "john.pierce@gmail.com",
                "SecretPass1583!`");
    }

    public User createValidUser() {
        userService.create(createValidUserDto());
        return userRepository.findByEmail("john.pierce@gmail.com")
                .orElseThrow(() -> new ResourceNotFoundException("NotFound"));
    }

    public void createAndSaveUser() {
        User user = User.builder().email("john.pierce@gmail.com")
                .firstName("John").lastName("Pierce").password("SecretPass1583!`").build();
        userRepository.save(user);
    }

    public void createUserWithSpaceInFirstName() {
        CreateUserDto validateUserDto = new CreateUserDto("Joh n", "Pierce",
                "john.pierce@gmail.com", "SecretPass1583!`");
        userService.create(validateUserDto);
    }

    public void createUserWithSpaceInLastName() {
        CreateUserDto validateUserDto = new CreateUserDto("John", "Pie rc e",
                "john.pierce@gmail.com", "SecretPass1583!`");
        userService.create(validateUserDto);
    }

    public void createUserWithInvalidPassword() {
        CreateUserDto validateUserDto = new CreateUserDto("John", "Pierce",
                "john.pierce@gmail.com", "invalidPassword");
        userService.create(validateUserDto);
    }

    public void createUserWithNumbersInFirstName() {
        CreateUserDto validateUserDto = new CreateUserDto("John23", "Pierce",
                "john.pierce@gmail.com", "SecretPass1583!`");
        userService.create(validateUserDto);
    }

    public void createUserWithNumbersInLastName() {
        CreateUserDto validateUserDto = new CreateUserDto("John", "Pierce4312",
                "john.pierce@gmail.com", "SecretPass1583!`");
        userService.create(validateUserDto);
    }

    public void createUserWithFirstNameEmpty() {
        CreateUserDto validateUserDto = new CreateUserDto("", "Pierce",
                "john.pierce@gmail.com", "SecretPass1583!`");
        userService.create(validateUserDto);
    }

    public void createUserWithLastNameEmpty() {
        CreateUserDto validateUserDto = new CreateUserDto("John", "",
                "john.pierce@gmail.com", "SecretPass1583!`");
        userService.create(validateUserDto);
    }

    public void createUserWithFirstNameFilledWithEmptySpaces() {
        CreateUserDto validateUserDto = new CreateUserDto("   ", "Pierce",
                "john.pierce@gmail.com", "SecretPass1583!`");
        userService.create(validateUserDto);
    }

    public void createUserWithLastNameFilledWithEmptySpaces() {
        CreateUserDto validateUserDto = new CreateUserDto("John", "    ",
                "john.pierce@gmail.com", "SecretPass1583!`");
        userService.create(validateUserDto);
    }

    public void createUserWhenEmailAlreadyExist() {
        createValidUser();
        CreateUserDto validateUserDto = new CreateUserDto("John", "Pierce",
                "john.pierce@gmail.com", "SecretPass1583!`");
        userService.create(validateUserDto);
    }

    public AuthenticationTokenDto validLoginDto() {
        User user = createAndSaveUserHelper();
        LoginUserDto dto = new LoginUserDto(user.getEmail(), user.getPassword());
        return userService.userLogin(dto);
    }

    public void userLoginWithInvalidEmailAndValidPassword() {
        User user = createAndSaveUserHelper();
        LoginUserDto dto = new LoginUserDto("wrong@email.com", user.getPassword());
        userService.userLogin(dto);
    }

    public void userLoginWithInvalidEmailAndInvalidPassword() {
        LoginUserDto dto = new LoginUserDto("wrong@email.com", "wrongPassword");
        userService.userLogin(dto);
    }

    public void userLoginWithValidEmailAndInvalidPassword() {
        User user = createAndSaveUserHelper();
        LoginUserDto dto = new LoginUserDto(user.getEmail(), "wrongPassword");
        userService.userLogin(dto);
    }

    public void userLoginWithNullEmailAndPassword() {
        User user = createAndSaveUserHelper();
        LoginUserDto dto = new LoginUserDto(null, user.getPassword());
        userService.userLogin(dto);
    }

    public void userLoginWithNullEmailAndNullPassword() {
        LoginUserDto dto = new LoginUserDto(null, null);
        userService.userLogin(dto);
    }

    public void userLoginWithEmailAndNullPassword() {
        User user = createAndSaveUserHelper();
        LoginUserDto dto = new LoginUserDto(user.getEmail(), null);
        userService.userLogin(dto);
    }

    public void userLoginWithEmptyEmailAndPassword() {
        User user = createAndSaveUserHelper();
        LoginUserDto dto = new LoginUserDto("", user.getPassword());
        userService.userLogin(dto);
    }

    public void userLoginWithEmptyEmailAndEmptyPassword() {
        LoginUserDto dto = new LoginUserDto("", "");
        userService.userLogin(dto);
    }

    public void userLoginWithEmailAndEmptyPassword() {
        User user = createAndSaveUserHelper();
        LoginUserDto dto = new LoginUserDto(user.getEmail(), "");
        userService.userLogin(dto);
    }

    private User createAndSaveUserHelper() {
        Role role = Role.builder().type(RoleType.CUSTOMER).build();
        roleRepository.save(role);
        User user = User.builder().email("john.pierce@gmail.com")
                .firstName("John").lastName("Pierce").password("SecretPass1583!`").cart(new Cart()).role(role).build();
        userRepository.save(user);
        return user;
    }

}
