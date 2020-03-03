package ro.fortech.internship.vinylshop.user.service;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.common.exception.InvalidException;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends BaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @After
    public void tearDown() {
        userSetup.deleteDatabase();
    }

    @Test
    public void firstNameDbWhenUserInsertsSpacesInFirstNameTest() {
        userSetup.createUserWithSpaceInFirstName();
        String firstName = "John";
        assertEquals(firstName, userSetup.userFromRepositoryFindByEmail("john.pierce@gmail.com").getFirstName());
    }

    @Test
    public void lastNameDbWhenUserInsertsSpaceInLastNameTest() {
        userSetup.createUserWithSpaceInLastName();
        String lastName = "Pierce";
        assertEquals(lastName, userSetup.userFromRepositoryFindByEmail("john.pierce@gmail.com").getLastName());
    }

    @Test
    public void exMessageWhenPasswordIsInvalidTest() {
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("Password is invalid");
        userSetup.createUserWithInvalidPassword();
    }

    @Test
    public void exMessageWhenFirstNameContainsNumbersTest() {
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("First name is invalid, please use only letters");
        userSetup.createUserWithNumbersInFirstName();
    }

    @Test
    public void exMessageWhenLastNameContainsNumbersTest() {
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("Last name is invalid, please use only letters");
        userSetup.createUserWithNumbersInLastName();
    }

    @Test
    public void exMessageWhenFirstNameIsEmptyTest() {
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("First name is invalid, field can't be empty");
        userSetup.createUserWithFirstNameEmpty();
    }

    @Test
    public void exMessageWhenLastNameIsEmptyTest() {
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("Last name is invalid, field can't be empty");
        userSetup.createUserWithLastNameEmpty();
    }

    @Test
    public void exMessageWhenFirstNameIsFilledWithEmptySpacesTest() {
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("First name is invalid, field can't be empty");
        userSetup.createUserWithFirstNameFilledWithEmptySpaces();
    }

    @Test
    public void exMessageWhenLastNameIsFilledWIthEmptySpacesTest() {
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("Last name is invalid, field can't be empty");
        userSetup.createUserWithLastNameFilledWithEmptySpaces();
    }

    @Test
    public void exMessageWhenEmailAlreadyExist() {
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("Email already exist");
        userSetup.createUserWhenEmailAlreadyExist();
    }

}
