package ro.fortech.internship.vinylshop.user.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.user.dto.CreateUserDto;
import ro.fortech.internship.vinylshop.user.dto.DeleteUserDto;
import ro.fortech.internship.vinylshop.user.dto.LoginUserDto;
import ro.fortech.internship.vinylshop.user.model.User;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;

public class UserControllerTest extends BaseTest {

    @After
    public void tearDown() {
        userSetup.deleteUsersDatabase();
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

    @Test
    public void userLoginWithInvalidEmailTest() {
        User user = userSetup.createValidUser();
        LoginUserDto dto = new LoginUserDto("wrong@email.com", user.getPassword());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void userLoginWithInvalidEmailInvalidPasswordTest() {
        LoginUserDto dto = new LoginUserDto("wrong@email.com", "wrongPassword");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void userLoginWithValidEmailAndInvalidPasswordTest() {
        User user = userSetup.createValidUser();
        LoginUserDto dto = new LoginUserDto(user.getEmail(), "wrongPassword");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void userLoginWithNullEmailAndNullPasswordTest() {
        LoginUserDto dto = new LoginUserDto(null, null);
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void userLoginWithEmailAndNullPasswordTest() {
        User user = userSetup.createValidUser();
        LoginUserDto dto = new LoginUserDto(user.getEmail(), null);
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void userLoginWithEmptyEmailAndEmptyPasswordTest() {
        LoginUserDto dto = new LoginUserDto("", "");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void userLoginWithEmailAndEmptyPasswordTest() {
        User user = userSetup.createValidUser();
        LoginUserDto dto = new LoginUserDto(user.getEmail(), "");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void deleteUserTest() {
        User user = userSetup.createValidUser();
        DeleteUserDto deleteUserDto = new DeleteUserDto(user.getEmail(), user.getPassword());
        UUID id = user.getId();
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/{id}"),
                HttpMethod.DELETE, new HttpEntity<>(deleteUserDto), String.class, id);
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteUserBadCredentialsTest() {
        User user = userSetup.createValidUser();
        DeleteUserDto deleteUserDto = new DeleteUserDto("somethingRandom@gmail.com", user.getPassword());
        UUID id = user.getId();
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/{id}"),
                HttpMethod.DELETE, new HttpEntity<>(deleteUserDto), String.class, id);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void deleteUserWithWrongIdTest() {
        User user = userSetup.createValidUser();
        DeleteUserDto deleteUserDto = new DeleteUserDto(user.getEmail(), user.getPassword());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/{id}"),
                HttpMethod.DELETE, new HttpEntity<>(deleteUserDto), String.class, UUID.randomUUID());
        Assert.assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getAllCustomersTest() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/customers"),
                GET, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(OK));
    }
}
