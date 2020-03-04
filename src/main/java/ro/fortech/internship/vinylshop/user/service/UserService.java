package ro.fortech.internship.vinylshop.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.fortech.internship.vinylshop.common.exception.InvalidException;
import ro.fortech.internship.vinylshop.common.exception.InvalidPasswordOrEmailException;
import ro.fortech.internship.vinylshop.common.exception.UserNotFoundException;
import ro.fortech.internship.vinylshop.user.converter.DtoConverter;
import ro.fortech.internship.vinylshop.user.dto.AuthenticationTokenDTO;
import ro.fortech.internship.vinylshop.user.dto.CreateUserDto;
import ro.fortech.internship.vinylshop.user.dto.DeleteUserDto;
import ro.fortech.internship.vinylshop.user.dto.LoginUserDto;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final DtoConverter dtoConverter;

    @Autowired
    public UserService(UserRepository userRepository, DtoConverter dtoConverter) {
        this.userRepository = userRepository;
        this.dtoConverter = dtoConverter;
    }

    public void create(CreateUserDto createUserDto) {
        User user = dtoConverter.toUserFromCreateUserDto(createUserDto);

        try {
            userRepository.save(user);
            log.info("User created");
        } catch (DataIntegrityViolationException e) {
            log.error("Email already exist", e);
            throw new InvalidException("Email already exist");
        }
    }

    public void deleteUser(UUID id, DeleteUserDto deleteUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Email address or/and password are invalid"));
        if (user.getPassword().equals(deleteUserDto.getPassword()) && user.getEmail().equals(deleteUserDto.getEmail())) {
            userRepository.delete(user);
            log.info("User with UUID {} deleted!", user.getId());
        } else {
            throw new InvalidPasswordOrEmailException("Invalid email or password!");
        }
    }

    public AuthenticationTokenDTO userLogin(LoginUserDto loginUserDTO) {
        log.info("Login requested for user {}", loginUserDTO.getEmail());
        User user = userRepository.findByEmailAndPassword(loginUserDTO.getEmail(), loginUserDTO.getPassword())
                .orElseThrow(() -> new InvalidPasswordOrEmailException("Invalid email or password!"));
        return new AuthenticationTokenDTO("tokenValue");
    }
}
