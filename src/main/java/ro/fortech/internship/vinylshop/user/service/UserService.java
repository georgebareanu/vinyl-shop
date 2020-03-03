package ro.fortech.internship.vinylshop.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.fortech.internship.vinylshop.common.exception.InvalidException;
import ro.fortech.internship.vinylshop.user.converter.DtoConverter;
import ro.fortech.internship.vinylshop.user.dto.CreateUserDto;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

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
}
