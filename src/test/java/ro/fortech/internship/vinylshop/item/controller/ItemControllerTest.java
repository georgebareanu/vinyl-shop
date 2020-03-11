package ro.fortech.internship.vinylshop.item.controller;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.item.dto.CreateOrUpdateItemDto;
import ro.fortech.internship.vinylshop.item.repository.ItemRepository;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

public class ItemControllerTest extends BaseTest {

    @Autowired
    private ItemRepository itemRepository;

    @After
    public void tearDown() {
        itemSetup.deleteItemsFromDb();
    }

    @Test
    public void itemCreateTest() {
        CreateOrUpdateItemDto validItem = itemSetup.createItemDto(10, "Bob Marley");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls"),
                POST, new HttpEntity<>(validItem), String.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
    }

    @Test
    public void itemCreateWithZeroQuantityTest() {
        CreateOrUpdateItemDto validItem = itemSetup.createItemDto(0, "Bob Marley");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls"),
                POST, new HttpEntity<>(validItem), String.class);
        assertThat(response.getStatusCode(), equalTo(CREATED));
    }

    @Test
    public void itemCreateWithNegativeQuantityTest() {
        CreateOrUpdateItemDto validItem = itemSetup.createItemDto(-16, "Bob Marley");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls"),
                POST, new HttpEntity<>(validItem), String.class);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void itemCreateWithEmptyNameTest() {
        CreateOrUpdateItemDto validItem = itemSetup.createItemDto(-16, "");
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls"),
                POST, new HttpEntity<>(validItem), String.class);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void deleteItemValidIdTest() {
        HttpHeaders headers = new HttpHeaders();
        itemSetup.createValidItemAndSave();
        UUID itemId = itemRepository.findByName("Bob Marley").getId();

        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls/{id}"),
                DELETE, new HttpEntity<>(headers), String.class, itemId);
        assertThat(response.getStatusCode(), equalTo(NO_CONTENT));
    }

    @Test
    public void deleteItemInvalidId() {
        HttpHeaders headers = new HttpHeaders();
        itemSetup.createValidItemAndSave();

        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls/{id}"),
                DELETE, new HttpEntity<>(headers), String.class, UUID.randomUUID());
        assertThat(response.getStatusCode(), equalTo(NOT_FOUND));
    }

    @Test
    public void updateSuccessfulTest() {
        itemSetup.createValidItemAndSave();
        UUID itemId = itemRepository.findByName("Bob Marley").getId();
        CreateOrUpdateItemDto createOrUpdateItemDto = itemSetup.createItemDto(60, "Guns'N'Roses");

        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls/{id}"),
                PUT, new HttpEntity<>(createOrUpdateItemDto), String.class, itemId);
        assertThat(response.getStatusCode(), equalTo(OK));
    }

    @Test
    public void updateEmptyNameTest() {
        itemSetup.createValidItemAndSave();
        UUID itemId = itemRepository.findByName("Bob Marley").getId();
        CreateOrUpdateItemDto createOrUpdateItemDto = itemSetup.createItemDto(60, "");

        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls/{id}"),
                PUT, new HttpEntity<>(createOrUpdateItemDto), String.class, itemId);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void updateNegativeStockTest() {
        itemSetup.createValidItemAndSave();
        UUID itemId = itemRepository.findByName("Bob Marley").getId();
        CreateOrUpdateItemDto createOrUpdateItemDto = itemSetup.createItemDto(-3, "Guns'N'Roses");

        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls/{id}"),
                PUT, new HttpEntity<>(createOrUpdateItemDto), String.class, itemId);
        assertThat(response.getStatusCode(), equalTo(BAD_REQUEST));
    }

    @Test
    public void getItemsTest() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> response = restTemplate.exchange(createUrl("api/vinyls"),
                GET, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCode(), equalTo(OK));
    }
}
