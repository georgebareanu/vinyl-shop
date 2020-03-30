package ro.fortech.internship.vinylshop.order.controller;

import org.junit.Test;
import org.springframework.http.*;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.user.model.User;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class OrderControllerTest extends BaseTest {

    @Test
    public void displayOrdersFromUserTest() {
        User user = userSetup.createValidUser();
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/orders"),
                HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void invalidUserIdTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", UUID.randomUUID().toString());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/orders"),
                HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void placeOrderWhenUserNotExistsTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", UUID.randomUUID().toString());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/orders"),
                HttpMethod.POST, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void placeOrderWhenCartIsEmptyTest() {
        User user = userSetup.createValidUser();
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/orders"),
                HttpMethod.POST, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void placeOrderWithOneItemInCartTest() {
        User user = userSetup.createValidUser();
        itemSetup.createValidItemAndSave();
        cartSetupTest.addToCartWithVariableQuantity(1);
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", user.getId().toString());
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/users/orders"),
                HttpMethod.POST, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }
}
