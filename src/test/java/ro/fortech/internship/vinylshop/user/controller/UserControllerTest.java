package ro.fortech.internship.vinylshop.user.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.user.dto.CreateUserDto;
import ro.fortech.internship.vinylshop.user.dto.LoginUserDto;
import ro.fortech.internship.vinylshop.user.model.User;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

public class UserControllerTest extends BaseTest {

    @After
    public void tearDown() {
        userSetup.deleteDatabase();
    }

    @Test
    public void allFieldsAreValidTest() {
        CreateUserDto validUserDto = userSetup.createValidUserDto();
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                POST, new HttpEntity<>(validUserDto), String.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
    }

    @Test
    public void userEmailAlreadyExistTest() {
        userSetup.createAndSaveUser();
        CreateUserDto validUserDto = userSetup.createValidUserDto();
        ResponseEntity<String> response2 = restTemplate.exchange(createUrl("/api/users"),
                POST, new HttpEntity<>(validUserDto), String.class);
        assertThat(response2.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void passwordIsInvalidTest() {
        CreateUserDto validUserDto = userSetup.createValidUserDto();
        validUserDto.setPassword("invalidPassword");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                POST, new HttpEntity<>(validUserDto), String.class);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void firstNameIsEmptyTest() {
        CreateUserDto validUserDto = userSetup.createValidUserDto();
        validUserDto.setFirstName("");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                POST, new HttpEntity<>(validUserDto), String.class);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void lastNameIsEmptyTest() {
        CreateUserDto validUserDto = userSetup.createValidUserDto();
        validUserDto.setLastName("");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                POST, new HttpEntity<>(validUserDto), String.class);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void firstNameContainsNumbersTest() {
        CreateUserDto validUserDto = userSetup.createValidUserDto();
        validUserDto.setFirstName("John34");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                POST, new HttpEntity<>(validUserDto), String.class);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void lastNameContainsNumbersTest() {
        CreateUserDto validUserDto = userSetup.createValidUserDto();
        validUserDto.setLastName("Pierce234");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                POST, new HttpEntity<>(validUserDto), String.class);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void userShouldLoginTest() {
        User user = userSetup.createValidUser();
        LoginUserDto loginUserDto = new LoginUserDto(user.getEmail(), user.getPassword());

        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(loginUserDto), String.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("{\"token\":\"tokenValue\"}", response.getBody());
    }

}
