package ro.fortech.internship.vinylshop.cart.service;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.cart.repository.CartRepository;
import ro.fortech.internship.vinylshop.common.exception.InvalidQuantityException;
import ro.fortech.internship.vinylshop.common.exception.ResourceNotFoundException;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CartServiceTest extends BaseTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @After
    public void tearDown() {
        userSetup.deleteUsersDatabase();
        cartSetupTest.deleteItemsFromDatabase();
    }

    @Test
    public void cartIsEmptyJsonTest() {
        User user = userSetup.createValidUser();
        assertTrue(user.getCart().getItemsInCart().isEmpty());
    }

    @Test
    public void CartIsFilledJsonTest() {
        User user = userSetup.createValidUser();
        cartSetupTest.addItemsInCart(user);
        assertFalse(user.getCart().getItemsInCart().isEmpty());
    }


    @Test
    public void addToCartWithNotEnoughStockTest() {
        expectedException.expect(InvalidQuantityException.class);
        User user = userSetup.createValidUser();
        cartSetupTest.addToCartWithVariableQuantity(user.getId(), 15);
    }

    @Test
    public void removeFromCartTest() {
        User user = userSetup.createValidUser();
        UUID cartItemId = cartSetupTest.addItemsInCart(user);
        cartSetupTest.removeCartItemFromCart(user.getId(), cartItemId);
        user = userRepository.findById(user.getId()).orElse(null);
        assertTrue(user.getCart().getItemsInCart().isEmpty());
    }

    @Test
    public void removeAnItemThatDoesNotExistsTest() {
        expectedException.expect(ResourceNotFoundException.class);
        User user = userSetup.createValidUser();
        UUID cartItemId = UUID.randomUUID();
        cartSetupTest.removeCartItemFromCart(user.getId(), cartItemId);
        user = userRepository.findById(user.getId()).orElse(null);
        assertTrue(user.getCart().getItemsInCart().isEmpty());
    }


}
