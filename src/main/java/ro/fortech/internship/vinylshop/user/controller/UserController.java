package ro.fortech.internship.vinylshop.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.fortech.internship.vinylshop.order.dto.DisplayOrderDto;
import ro.fortech.internship.vinylshop.user.dto.*;
import ro.fortech.internship.vinylshop.user.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CreateUserDto createUserDto) {
        userService.create(createUserDto);
    }

    @PostMapping(value = "/login")
    public AuthenticationTokenDto login(@RequestBody LoginUserDto loginUserDto) {
        return userService.userLogin(loginUserDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @RequestBody DeleteUserDto deleteUserDto) {
        userService.delete(deleteUserDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DisplayUserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/{userId}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<DisplayOrderDto> getUserOrders(@PathVariable UUID userId) {
        return userService.getUserOrders(userId);
    }
}
