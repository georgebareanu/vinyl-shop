package ro.fortech.internship.vinylshop.item.controller;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.item.dto.CreateItemDto;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

public class ItemControllerTest extends BaseTest {

    @After
    public void tearDown() {
        itemSetup.deleteItemsFromDb();
    }

    @Test
    public void itemCreateTest() {
        CreateItemDto validItem = itemSetup.createItemDto(10, "Bob Marley");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls"),
                POST, new HttpEntity<>(validItem), String.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
    }

    @Test
    public void itemCreateWithZeroQuantityTest() {
        CreateItemDto validItem = itemSetup.createItemDto(0, "Bob Marley");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls"),
                POST, new HttpEntity<>(validItem), String.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
    }

    @Test
    public void itemCreateWithNegativeQuantityTest() {
        CreateItemDto validItem = itemSetup.createItemDto(-16, "Bob Marley");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls"),
                POST, new HttpEntity<>(validItem), String.class);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void itemCreateWithEmptyNameTest() {
        CreateItemDto validItem = itemSetup.createItemDto(-16, "");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls"),
                POST, new HttpEntity<>(validItem), String.class);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }
}
