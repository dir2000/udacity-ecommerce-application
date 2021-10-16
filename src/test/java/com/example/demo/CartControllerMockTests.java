package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
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
}
