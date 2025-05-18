package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    private Item item;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        item = new Item("Test Item", "This is a test item");
    }

    @Test
    public void testGetAllItems() {
        List<Item> items = new ArrayList<>();
        items.add(item);

        when(itemService.findAll()).thenReturn(items);

        List<Item> result = itemController.getAllItems();

        assertEquals(1, result.size());
        assertEquals("Test Item", result.get(0).getName());
    }

    @Test
    public void testGetItemByIdFound() {
        when(itemService.findById(1L)).thenReturn(Optional.of(item));

        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertEquals(ResponseEntity.ok(item), response);
    }

    @Test
    public void testGetItemByIdNotFound() {
        when(itemService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    public void testCreateItem() {
        when(itemService.save(any(Item.class))).thenReturn(item);

        Item createdItem = itemController.createItem(item);

        assertEquals("Test Item", createdItem.getName());
        verify(itemService).save(item);
    }

    @Test
    public void testUpdateItemFound() {
        when(itemService.findById(1L)).thenReturn(Optional.of(item));
        when(itemService.save(any(Item.class))).thenReturn(item);

        ResponseEntity<Item> response = itemController.updateItem(1L, item);

        assertEquals(ResponseEntity.ok(item), response);
        verify(itemService).save(item);
    }

    @Test
    public void testUpdateItemNotFound() {
        when(itemService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Item> response = itemController.updateItem(1L, item);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    public void testDeleteItemFound() {
        when(itemService.findById(1L)).thenReturn(Optional.of(item));

        ResponseEntity<Void> response = itemController.deleteItem(1L);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(itemService).deleteById(1L);
    }

    @Test
    public void testDeleteItemNotFound() {
        when(itemService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = itemController.deleteItem(1L);

        assertEquals(ResponseEntity.notFound().build(), response);
    }
}
