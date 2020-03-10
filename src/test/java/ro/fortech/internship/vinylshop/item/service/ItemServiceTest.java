package ro.fortech.internship.vinylshop.item.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import ro.fortech.internship.vinylshop.BaseTest;
import ro.fortech.internship.vinylshop.item.model.Item;
import ro.fortech.internship.vinylshop.item.model.ItemStatus;
import ro.fortech.internship.vinylshop.item.repository.ItemRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ItemServiceTest extends BaseTest {

    @Autowired
    private ItemRepository itemRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void createValidItem() {
        itemSetup.createValidItemAndSave();
        String name = "Bob Marley";
        assertEquals(name, itemRepository.findByName("Bob Marley").getName());
    }

    @Test
    public void createItemWithEmptyStick() {
        itemSetup.createItemWithZeroQuantityAndSave();
        assertEquals(ItemStatus.INSUFFICIENT_STOCK, itemRepository.findByName("Bob Marley").getStatus());
    }

    @Test
    public void deleteItemTest() {
        itemSetup.createValidItemAndSave();
        Item item = itemRepository.findByName("Bob Marley");
        itemRepository.delete(item);
        assertNull(itemRepository.findByName("Bob Marley"));
    }
}
