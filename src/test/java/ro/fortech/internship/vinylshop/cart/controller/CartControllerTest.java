package ro.fortech.internship.vinylshop.cart.controller;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.*;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.cartitem.dto.CartItemAddToCardDto;
import ro.fortech.internship.vinylshop.user.model.User;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CartControllerTest extends BaseTest {

    @After
    public void tearDown() {
        userSetup.deleteUsersDatabase();
        cartSetupTest.deleteItemsFromDatabase();
    }

    @Test
    public void cartIsFilledSuccessfulTest() {
        User user = userSetup.createValidUser();
        cartSetupTest.addItemsInCart(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/cart"),
                HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void cartIsEmptySuccessfulTest() {
        User user = userSetup.createValidUser();
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/cart"),
                HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void addToCartTest() {
        User user = userSetup.createValidUser();
        UUID itemId = cartSetupTest.createItem();
        CartItemAddToCardDto cartItemAddToCardDto = new CartItemAddToCardDto(itemId, 3);

        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        HttpEntity<CartItemAddToCardDto> request = new HttpEntity<>(cartItemAddToCardDto, headers);

        ResponseEntity<String> response = this.restTemplate.exchange(createUrl("api/users/cart"),
                HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void addToCartWithNegativeQuantityTest() {
        User user = userSetup.createValidUser();
        UUID itemId = cartSetupTest.createItem();
        CartItemAddToCardDto cartItemAddToCardDto = new CartItemAddToCardDto(itemId, -5);

        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        HttpEntity<CartItemAddToCardDto> request = new HttpEntity<>(cartItemAddToCardDto, headers);

        ResponseEntity<String> response = this.restTemplate.exchange(createUrl("api/users/cart"),
                HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void addToCartWithZeroQuantityTest() {
        User user = userSetup.createValidUser();
        UUID itemId = cartSetupTest.createItem();
        CartItemAddToCardDto cartItemAddToCardDto = new CartItemAddToCardDto(itemId, 0);

        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        HttpEntity<CartItemAddToCardDto> request = new HttpEntity<>(cartItemAddToCardDto, headers);

        ResponseEntity<String> response = this.restTemplate.exchange(createUrl("api/users/cart"),
                HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void addToCartWithNotEnoughStockTest() {
        User user = userSetup.createValidUser();
        UUID itemId = cartSetupTest.createItem();
        CartItemAddToCardDto cartItemAddToCardDto = new CartItemAddToCardDto(itemId, 45);

        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        HttpEntity<CartItemAddToCardDto> request = new HttpEntity<>(cartItemAddToCardDto, headers);

        ResponseEntity<String> response = this.restTemplate.exchange(createUrl("api/users/cart"),
                HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void removeVinylSuccessfulTest() {
        User user = userSetup.createValidUser();
        UUID cartItemId = cartSetupTest.addItemsInCart(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        HttpEntity<UUID> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = this.restTemplate.exchange(createUrl("api/users/cart/{itemId}"),
                HttpMethod.DELETE, request, String.class, cartItemId);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
    }

    @Test
    public void removeVinylThatIsNotPresentInCartTest() {
        User user = userSetup.createValidUser();

        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        HttpEntity<UUID> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = this.restTemplate.exchange(createUrl("api/users/cart/{itemId}"),
                HttpMethod.DELETE, request, String.class, UUID.randomUUID());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

}
