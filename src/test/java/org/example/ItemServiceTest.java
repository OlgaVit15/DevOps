package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Item item1 = new Item("Item1", "Item 1");
        Item item2 = new Item("Item1", "Item 2");
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<Item> items = itemService.findAll();

        assertNotNull(items);
        assertEquals(2, items.size());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Item item = new Item("Item 1", "Item1");
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Optional<Item> foundItem = itemService.findById(1L);

        assertTrue(foundItem.isPresent());
        assertEquals("Item 1", foundItem.get().getName());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        Item item = new Item("Item 1", "Item 1");
        when(itemRepository.save(item)).thenReturn(item);

        Item savedItem = itemService.save(item);

        assertNotNull(savedItem);
        assertEquals("Item 1", savedItem.getName());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testDeleteById() {
        Long idToDelete = 1L;

        itemService.deleteById(idToDelete);

        verify(itemRepository, times(1)).deleteById(idToDelete);
    }
}
