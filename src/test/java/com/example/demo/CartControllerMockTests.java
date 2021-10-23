package com.example.demo;

import com.example.demo.controllers.CartController;
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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CartControllerMockTests {
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private CartRepository mockCartRepository;
    @Mock
    private ItemRepository mockItemRepository;
    @InjectMocks
    private CartController cartController;

    private String username = "username";

    @Test
    public void addToCartForNonExistentUser() {
        //given
        when(mockUserRepository.findByUsername(any())).thenReturn(null);
        //when
        ResponseEntity<Cart> responseEntity = cartController.addToCart(new ModifyCartRequest());
        //then
        HttpStatus expected = HttpStatus.NOT_FOUND;
        HttpStatus actual = responseEntity.getStatusCode();
        assertEquals(expected, actual);
    }

    @Test
    public void addNonExistentItemToCart() {
        //given
        when(mockUserRepository.findByUsername(username)).thenReturn(new User());
        when(mockItemRepository.findById(any())).thenReturn(Optional.empty());
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(username);
        //when
        ResponseEntity<Cart> responseEntity = cartController.addToCart(request);
        //then
        HttpStatus expected = HttpStatus.NOT_FOUND;
        HttpStatus actual = responseEntity.getStatusCode();
        assertEquals(expected, actual);
    }

    @Test
    public void addItemToCart() {
        //given
        User user = new User();
        user.setCart(new Cart());
        when(mockUserRepository.findByUsername(username)).thenReturn(user);
        Item item = new Item();
        item.setId(1L);
        when(mockItemRepository.findById(any())).thenReturn(Optional.of(item));
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(username);
        request.setItemId(item.getId());
        request.setQuantity(1);
        //when
        ResponseEntity<Cart> responseEntity = cartController.addToCart(request);
        //then
        HttpStatus expected = HttpStatus.OK;
        HttpStatus actual = responseEntity.getStatusCode();
        assertEquals(expected, actual);
    }

    @Test
    public void removeFromCartForNonExistentUser() {
        //given
        when(mockUserRepository.findByUsername(any())).thenReturn(null);
        //when
        ResponseEntity<Cart> responseEntity = cartController.removeFromСart(new ModifyCartRequest());
        //then
        HttpStatus expected = HttpStatus.NOT_FOUND;
        HttpStatus actual = responseEntity.getStatusCode();
        assertEquals(expected, actual);
    }

    @Test
    public void removeNonExistentItemFromCart() {
        //given
        when(mockUserRepository.findByUsername(username)).thenReturn(new User());
        when(mockItemRepository.findById(any())).thenReturn(Optional.empty());
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(username);
        //when
        ResponseEntity<Cart> responseEntity = cartController.removeFromСart(request);
        //then
        HttpStatus expected = HttpStatus.NOT_FOUND;
        HttpStatus actual = responseEntity.getStatusCode();
        assertEquals(expected, actual);
    }

    @Test
    public void removeItemFromCart() {
        //given
        User user = new User();
        user.setCart(new Cart());
        when(mockUserRepository.findByUsername(username)).thenReturn(user);
        when(mockItemRepository.findById(any())).thenReturn(Optional.of(new Item()));
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(username);
        //when
        ResponseEntity<Cart> responseEntity = cartController.removeFromСart(request);
        //then
        HttpStatus expected = HttpStatus.OK;
        HttpStatus actual = responseEntity.getStatusCode();
        assertEquals(expected, actual);
    }
}
