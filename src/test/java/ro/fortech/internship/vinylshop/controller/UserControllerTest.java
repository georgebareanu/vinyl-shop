package ro.fortech.internship.vinylshop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ro.fortech.internship.vinylshop.data.UserDataProvider;
import ro.fortech.internship.vinylshop.role.model.RoleType;
import ro.fortech.internship.vinylshop.user.dto.CreateUserDto;
import ro.fortech.internship.vinylshop.user.dto.DeleteUserDto;
import ro.fortech.internship.vinylshop.user.dto.LoginUserDto;
import ro.fortech.internship.vinylshop.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

public class UserControllerTest extends BaseControllerForTests {

    @Test
    public void register_whenAllFieldsAreValidInCreateUserDto_expectCreatedReturned() {
        CreateUserDto validUserDto = UserDataProvider.createCreateUserDto("john.pierce@gmail.com",
                "John", "Pierce", "SecretPass1583!`");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        assertEquals(response.getStatusCode(), CREATED);
    }

    @Test
    public void register_whenEmailAlreadyExists_expectBadRequestReturned() {
        CreateUserDto validUserDto = UserDataProvider.createCreateUserDto("john.pierce@gmail.com",
                "John", "Pierce", "SecretPass1583!`");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        ResponseEntity<String> response2 = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        assertEquals(response2.getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void register_whenFirstNameContainsNumbersInCreatedUserDto_expectBadRequestReturned() {
        CreateUserDto validUserDto = UserDataProvider.createCreateUserDto("john.pierce@gmail.com",
                "Jo123hn", "Pierce", "SecretPass1583!`");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        assertEquals(response.getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void register_whenLastNameContainsNumbersInCreatedUserDto_expectBadRequestReturned() {
        CreateUserDto validUserDto = UserDataProvider.createCreateUserDto("john.pierce@gmail.com",
                "John", "Pier123ce", "SecretPass1583!`");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        assertEquals(response.getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void register_whenFirstNameIsEmptyInCreatedUserDto_expectedBadRequestReturned() {
        CreateUserDto validUserDto = UserDataProvider.createCreateUserDto("john.pierce@gmail.com",
                "", "Pierce", "SecretPass1583!`");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        assertEquals(response.getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void register_whenLastNameIsEmptyInCreatedUserDto_expectedBadRequestReturned() {
        CreateUserDto validUserDto = UserDataProvider.createCreateUserDto("john.pierce@gmail.com",
                "John", "", "SecretPass1583!`");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        assertEquals(response.getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void register_whenPasswordIsInvalidNoNumber_expectedBadRequestReturned() {
        CreateUserDto validUserDto = UserDataProvider.createCreateUserDto("john.pierce@gmail.com",
                "John", "Pierce", "SecretPass!`");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        assertEquals(response.getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void register_whenPasswordIsInvalidNoSpecialCharacter_expectedBadRequestReturned() {
        CreateUserDto validUserDto = UserDataProvider.createCreateUserDto("john.pierce@gmail.com",
                "John", "Pierce", "SecretPass1654");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        assertEquals(response.getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void register_whenPasswordIsInvalidNoCapitalLetter_expectedBadRequestReturned() {
        CreateUserDto validUserDto = UserDataProvider.createCreateUserDto("john.pierce@gmail.com",
                "John", "Pierce", "secretpass1654!`");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        assertEquals(response.getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void register_whenPasswordIsInvalidNotEnoughCharacters_expectedBadRequestReturned() {
        CreateUserDto validUserDto = UserDataProvider.createCreateUserDto("john.pierce@gmail.com",
                "John", "Pierce", "S2s!`");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.POST, new HttpEntity<>(validUserDto), String.class);
        assertEquals(response.getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void login_whenCredentialsAreCorrect_expectedOkReturned() {
        User user = UserDataProvider.createValidUser();
        saveUserInDb(user, RoleType.CUSTOMER);
        LoginUserDto dto = new LoginUserDto(user.getEmail(), "stringQ!3");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        assertEquals(response.getStatusCode(), OK);
    }

    @Test
    public void login_whenPasswordIsWrong_expectedUnauthorizedReturned() {
        User user = UserDataProvider.createValidUser();
        saveUserInDb(user, RoleType.CUSTOMER);
        LoginUserDto dto = new LoginUserDto(user.getEmail(), "stringWrongQ!3");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        assertEquals(response.getStatusCode(), UNAUTHORIZED);
    }

    @Test
    public void login_whenEmailIsEmpty_expectedUnauthorizedReturned() {
        User user = UserDataProvider.createValidUser();
        saveUserInDb(user, RoleType.CUSTOMER);
        LoginUserDto dto = new LoginUserDto("", "stringQ!3");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        assertEquals(response.getStatusCode(), UNAUTHORIZED);
    }

    @Test
    public void login_whenEmailIsWrong_expectedUnauthorizedReturned() {
        User user = UserDataProvider.createValidUser();
        saveUserInDb(user, RoleType.CUSTOMER);
        LoginUserDto dto = new LoginUserDto("wrong@gmail.com", "stringQ!3");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/login"),
                HttpMethod.POST, new HttpEntity<>(dto), String.class);
        assertEquals(response.getStatusCode(), UNAUTHORIZED);
    }

    @Test
    public void getUsers_whenTokenIsValidFromManager_expectedOkReturned() {
        User user = UserDataProvider.createValidUser();
        String token = getValidToken(user, RoleType.MANAGER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertEquals(response.getStatusCode(), OK);
    }

    @Test
    public void getUsers_whenTokenIsValidFromCustomer_expectedForbiddenReturned() {
        User user = UserDataProvider.createValidUser();
        String token = getValidToken(user, RoleType.CUSTOMER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users"),
                HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertEquals(response.getStatusCode(), FORBIDDEN);
    }

    @Test
    public void getOrders_whenTokenIsValidFromManager_expectedOkReturned() {
        User user = UserDataProvider.createValidUser();
        user.setEmail("altceva@altceva");
        saveUserInDb(user, RoleType.CUSTOMER);
        User manager = UserDataProvider.createValidUser();
        String token = getValidToken(manager, RoleType.MANAGER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users/{userId}/orders"),
                HttpMethod.GET, new HttpEntity<>(headers), String.class, user.getId());
        assertEquals(response.getStatusCode(), OK);
    }

    @Test
    public void getOrders_whenTokenIsValidFromCustomer_expectedUnauthorizedReturned() {
        User user = UserDataProvider.createValidUser();
        user.setEmail("altceva@altceva");
        saveUserInDb(user, RoleType.CUSTOMER);
        User manager = UserDataProvider.createValidUser();
        String token = getValidToken(manager, RoleType.CUSTOMER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ResponseEntity<String> response = restTemplate.exchange(createUrl("/api/users/{userId}/orders"),
                HttpMethod.GET, new HttpEntity<>(headers), String.class, user.getId());
        assertEquals(response.getStatusCode(), FORBIDDEN);
    }

    @Test
    public void delete_whenEverythingIsValid_expectedNoContentReturned() {
        User user = UserDataProvider.createValidUser();
        String token = getValidToken(user, RoleType.CUSTOMER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        DeleteUserDto deleteUserDto = new DeleteUserDto(user.getEmail(), "stringQ!3");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/{id}"),
                HttpMethod.DELETE, new HttpEntity<>(deleteUserDto, headers), String.class, user.getId());
        assertEquals(response.getStatusCode(), NO_CONTENT);
    }

    @Test
    public void delete_whenEmailIsInvalid_expectedUnauthorizedReturned() {
        User user = UserDataProvider.createValidUser();
        String token = getValidToken(user, RoleType.CUSTOMER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        DeleteUserDto deleteUserDto = new DeleteUserDto("wrong@email", "stringQ!3");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/{id}"),
                HttpMethod.DELETE, new HttpEntity<>(deleteUserDto, headers), String.class, user.getId());
        assertEquals(response.getStatusCode(), UNAUTHORIZED);
    }

    @Test
    public void delete_whenPasswordIsInvalid_expectedUnauthorizedReturned() {
        User user = UserDataProvider.createValidUser();
        String token = getValidToken(user, RoleType.CUSTOMER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        DeleteUserDto deleteUserDto = new DeleteUserDto(user.getEmail(), "invalidPwd");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/{id}"),
                HttpMethod.DELETE, new HttpEntity<>(deleteUserDto, headers), String.class, user.getId());
        assertEquals(response.getStatusCode(), UNAUTHORIZED);
    }

}
