package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ItemControllerMockTests {
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemController itemController;

    @Test
    public void getItems() {
        //given
        List<Item> items = new ArrayList<>();
        when(itemRepository.findAll()).thenReturn(items);
        //when
        ResponseEntity<List<Item>> responseEntity = itemController.getItems();
        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(items, responseEntity.getBody());
    }

    @Test
    public void getExistentItemById() {
        //given
        Item item = new Item();
        Long id = 1L;
        item.setId(id);
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        //when
        ResponseEntity<Item> responseEntity = itemController.getItemById(id);
        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(item.getId(), responseEntity.getBody().getId());
    }

    @Test
    public void getNonExistentItemById() {
        //given
        when(itemRepository.findById(any())).thenReturn(Optional.empty());
        //when
        ResponseEntity<Item> responseEntity = itemController.getItemById(1L);
        //then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getItemsByName() {
        //given
        List<Item> expectedList = new ArrayList<>();
        expectedList.add(new Item());
        String name = "SomeUsername";
        when(itemRepository.findByName(name)).thenReturn(expectedList);
        //when
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName(name);
        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedList, responseEntity.getBody());
    }
}
