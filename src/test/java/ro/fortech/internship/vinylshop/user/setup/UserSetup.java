package ro.fortech.internship.vinylshop.user.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.user.dto.CreateUserDto;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;
import ro.fortech.internship.vinylshop.user.service.UserService;

@Component
public class UserSetup {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public void deleteDatabase() {
        userRepository.deleteAll();
    }

    public User userFromRepositoryFindByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public CreateUserDto createValidUserDto() {
        return new CreateUserDto("John", "Pierce", "john.pierce@gmail.com",
                "SecretPass1583!`");
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
        User user = User.builder().email("john.pierce@gmail.com")
                .firstName("John").lastName("Pierce").password("SecretPass1583!`").build();
        userRepository.save(user);

        CreateUserDto validateUserDto = new CreateUserDto("John", "Pierce",
                "john.pierce@gmail.com", "SecretPass1583!`");
        userService.create(validateUserDto);
    }
}
