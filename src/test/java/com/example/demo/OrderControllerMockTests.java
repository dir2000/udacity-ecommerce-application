package com.example.demo;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderControllerMockTests {
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderController orderController;

    private String username = "username";

    @Test
    public void SubmitOrderForNonExistentUser() {
        //given
        when(mockUserRepository.findByUsername(username)).thenReturn(null);
        //when
        ResponseEntity<UserOrder> responseEntity = orderController.submit(username);
        //then
        HttpStatus expected = HttpStatus.NOT_FOUND;
        HttpStatus actual = responseEntity.getStatusCode();
        assertEquals(expected, actual);
    }

    @Test
    public void SubmitOrderForExistentUser() {
        //given
        User user = new User();
        user.setCart(new Cart());
        when(mockUserRepository.findByUsername(username)).thenReturn(user);
        //when
        ResponseEntity<UserOrder> responseEntity = orderController.submit(username);
        //then
        HttpStatus expected = HttpStatus.OK;
        HttpStatus actual = responseEntity.getStatusCode();
        assertEquals(expected, actual);
    }

    @Test
    public void GetOrdersForNonExistentUser() {
        //given
        when(mockUserRepository.findByUsername(username)).thenReturn(null);
        //when
        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(username);
        //then
        HttpStatus expected = HttpStatus.NOT_FOUND;
        HttpStatus actual = responseEntity.getStatusCode();
        assertEquals(expected, actual);
    }

    @Test
    public void GetOrdersForExistentUser() {
        //given
        when(mockUserRepository.findByUsername(username)).thenReturn(new User());
        //when
        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(username);
        //then
        HttpStatus expected = HttpStatus.OK;
        HttpStatus actual = responseEntity.getStatusCode();
        assertEquals(expected, actual);
    }
}
