package ro.fortech.internship.vinylshop.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final DtoConverter dtoConverter;
    private final AuthenticatedUser authenticatedUser;
    private final JwtTokenUtil jwtTokenUtil;

    public List<DisplayUserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(dtoConverter::toDisplayUserDtoFromUser)
                .collect(Collectors.toList());
    }

    public void create(CreateUserDto createUserDto) {
        if (userRepository.findByEmail(createUserDto.getEmail()).isPresent()) {
            throw new InvalidException("Email already exist");
        }
        User user = dtoConverter.toUserFromCreateUserDto(createUserDto);

        log.info("User created");
        userRepository.save(user);
    }

    public void delete(DeleteUserDto deleteUserDto) {
        User user = authenticatedUser.getAuthenticatedUser();
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
        User user = userRepository.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> new InvalidPasswordOrEmailException("Email or Password incorrect!"));

        if (!BCrypt.checkpw(loginUserDTO.getPassword(), user.getPassword())) {
            throw new InvalidPasswordOrEmailException("Email or Password incorrect!");
        }

        String token = jwtTokenUtil.generate(user);
        return new AuthenticationTokenDto(user.getId(), token, user.getRole().getType());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), getRoles(user));
    }

    private Collection<? extends GrantedAuthority> getRoles(User user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getType().toString()));
    }
}
