package com.albert.currency.controller;

import com.albert.currency.controller.facade.CartControllerFacade;
import com.albert.currency.domain.dto.CartDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(CartController.class)
public class CartControllerTestSuite {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private CartControllerFacade cartControllerFacade;

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //GIVEN
        when(cartControllerFacade.fetchCarts()).thenReturn(List.of());
        //WHEN&THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldFetchCarts() throws Exception {
        //GIVEN
        List<Long> transactions = List.of(1L,2L);
        List<CartDto> carts = List.of(new CartDto(1L,1L,transactions));
        when(cartControllerFacade.fetchCarts()).thenReturn(carts);
        //WHEN&THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                //Cart Fields
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cartId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transactions",Matchers.hasSize(2)));

    }

    @Test
    public void shouldFetchCartById() throws Exception {
        //GIVEN
        List<Long> transactions = List.of(1L,2L);
        CartDto cartDto= new CartDto(1L,1L,transactions);
        when(cartControllerFacade.fetchCart(1L)).thenReturn(cartDto);
        //WHEN&THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cart/{cartId}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                //Cart Fields
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions",Matchers.hasSize(2)));

    }
}
