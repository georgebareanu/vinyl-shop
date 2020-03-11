package ro.fortech.internship.vinylshop.user.converter;

import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.common.exception.InvalidException;
import ro.fortech.internship.vinylshop.user.dto.CreateUserDto;
import ro.fortech.internship.vinylshop.user.dto.DisplayUserDto;
import ro.fortech.internship.vinylshop.user.model.User;

@Component
public class DtoConverter {

    private final PasswordValidator passwordValidator;

    @Autowired
    public DtoConverter(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    public User toUserFromCreateUserDto(CreateUserDto createUserDto) {
        validUserDto(createUserDto);
        User user = new User();
        user.setFirstName(createUserDto.getFirstName().replaceAll("\\s+", ""));
        user.setLastName(createUserDto.getLastName().replaceAll("\\s+", ""));
        user.setEmail(createUserDto.getEmail());
        user.setCart(new Cart());

        //todo: don't forget to encode the password when security is implemented
        user.setPassword(createUserDto.getPassword());
        return user;
    }

    public DisplayUserDto toDisplayUserDtoFromUser(User user) {
        DisplayUserDto displayUserDto = new DisplayUserDto();
        displayUserDto.setEmail(user.getEmail());
        displayUserDto.setFirstName(user.getFirstName());
        displayUserDto.setLastName(user.getLastName());
        return displayUserDto;
    }

    private void validUserDto(CreateUserDto createUserDto) {
        RuleResult result = passwordValidator.validate(new PasswordData(createUserDto.getPassword()));

        if (!result.isValid()) {
            throw new InvalidException("Password is invalid. Password must have at least one lower case letter, " +
                    "one capital letter, one digit and one special character");
        }
        if (createUserDto.getFirstName().matches(".*[0-9].*")) {
            throw new InvalidException("First name is invalid, please use only letters");
        }
        if (createUserDto.getFirstName().replaceAll("\\s+", "").isEmpty()) {
            throw new InvalidException("First name is invalid, field can't be empty");
        }
        if (createUserDto.getLastName().matches(".*[0-9].*")) {
            throw new InvalidException("Last name is invalid, please use only letters");
        }
        if (createUserDto.getLastName().replaceAll("\\s+", "").isEmpty()) {
            throw new InvalidException("Last name is invalid, field can't be empty");
        }
    }


}
