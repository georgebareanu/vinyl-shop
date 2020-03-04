package ro.fortech.internship.vinylshop.cart.service;

import org.junit.After;
import org.junit.Test;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.user.model.User;

import static org.junit.Assert.assertTrue;

public class CartServiceTest extends BaseTest {

    @After
    public void tearDown() {
        userSetup.deleteUsersDatabase();
    }

    @Test
    public void cartIsEmptyJsonTest() {
        User user = userSetup.createValidUser();
        assertTrue(user.getCart().getItemsInCart().isEmpty());
    }

}
