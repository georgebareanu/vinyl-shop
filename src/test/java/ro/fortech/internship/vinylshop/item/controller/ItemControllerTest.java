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
import static org.springframework.http.HttpStatus.CREATED;

public class ItemControllerTest extends BaseTest {

    @After
    public void tearDown() {
        itemSetup.deleteItemsFromDb();
    }

    @Test
    public void itemCreateTest() {
        CreateItemDto validItem = itemSetup.createItemDto();
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls"),
                POST, new HttpEntity<>(validItem), String.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
    }
}
