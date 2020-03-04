package ro.fortech.internship.vinylshop.cart.controller;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.*;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.user.model.User;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CartControllerTest extends BaseTest {

    @After
    public void tearDown() {
        userSetup.deleteUsersDatabase();
    }

    @Test
    public void cartIsFilledSuccessfulTest() {
        User user = userSetup.createValidUser();
        cartSetupTest.AddItemsInCart(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/cart"),
                HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void cartIsEmptySuccessfulTest(){
        User user = userSetup.createValidUser();
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/cart"),
                HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
}
