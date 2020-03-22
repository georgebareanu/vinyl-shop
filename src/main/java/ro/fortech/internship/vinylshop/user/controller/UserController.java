package ro.fortech.internship.vinylshop.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.fortech.internship.vinylshop.order.dto.DisplayOrderDto;
import ro.fortech.internship.vinylshop.user.dto.*;
import ro.fortech.internship.vinylshop.user.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CreateUserDto createUserDto) {
        userService.create(createUserDto);
    }

    @PostMapping(value = "/users/login")
    public AuthenticationTokenDto userLogin(@RequestBody LoginUserDto loginUserDto) {
        return userService.userLogin(loginUserDto);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id, @Valid @RequestBody DeleteUserDto deleteUserDto) {
        userService.deleteUser(id, deleteUserDto);
    }

    @GetMapping(value = "/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<DisplayUserDto> getCustomers() {
        return userService.getCustomers();
    }

    @GetMapping(value = "/users/{userId}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<DisplayOrderDto> getUserOrders(@PathVariable UUID userId) {
        return userService.getUserOrders(userId);
    }
}
