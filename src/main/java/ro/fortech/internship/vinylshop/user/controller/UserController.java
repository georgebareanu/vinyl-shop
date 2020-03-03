package ro.fortech.internship.vinylshop.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.fortech.internship.vinylshop.user.dto.CreateUserDto;
import ro.fortech.internship.vinylshop.user.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CreateUserDto createUserDto) {
        userService.create(createUserDto);
    }
}
