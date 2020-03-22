package ro.fortech.internship.vinylshop.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fortech.internship.vinylshop.common.exception.InvalidException;
import ro.fortech.internship.vinylshop.common.exception.InvalidPasswordOrEmailException;
import ro.fortech.internship.vinylshop.common.exception.ResourceNotFoundException;
import ro.fortech.internship.vinylshop.config.security.JwtTokenUtil;
import ro.fortech.internship.vinylshop.order.converter.OrderDtoConverter;
import ro.fortech.internship.vinylshop.order.dto.DisplayOrderDto;
import ro.fortech.internship.vinylshop.order.model.Order;
import ro.fortech.internship.vinylshop.user.converter.DtoConverter;
import ro.fortech.internship.vinylshop.user.dto.*;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final DtoConverter dtoConverter;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, DtoConverter dtoConverter, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.dtoConverter = dtoConverter;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public List<DisplayUserDto> getCustomers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(dtoConverter::toDisplayUserDtoFromUser)
                .collect(Collectors.toList());
    }

    public void create(CreateUserDto createUserDto) {
        User user = dtoConverter.toUserFromCreateUserDto(createUserDto);

        try {
            log.info("User created");
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Email already exist", e);
            throw new InvalidException("Email already exist");
        }
    }

    public void deleteUser(UUID id, DeleteUserDto deleteUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Email address or/and password are invalid"));
        if (user.getPassword().equals(deleteUserDto.getPassword()) && user.getEmail().equals(deleteUserDto.getEmail())) {
            userRepository.delete(user);
            log.info("User with UUID {} deleted!", user.getId());
        } else {
            throw new InvalidPasswordOrEmailException("Invalid email or password!");
        }
    }

    @Transactional(readOnly = true)
    public List<DisplayOrderDto> getUserOrders(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Order> orders = user.getOrders();
        return orders.stream()
                .map(OrderDtoConverter::toDisplayOrderDtoFromOrder)
                .collect(Collectors.toList());
    }

    public AuthenticationTokenDto userLogin(LoginUserDto loginUserDTO) {
        log.info("Login requested for user {}", loginUserDTO.getEmail());
        User user = userRepository.findByEmailAndPassword(loginUserDTO.getEmail(), loginUserDTO.getPassword())
                .orElseThrow(() -> new InvalidPasswordOrEmailException("Invalid email or password!"));
        String token = jwtTokenUtil.generate(user);
        return new AuthenticationTokenDto(user.getId(), token, user.getRoles().get(0));
    }
}
