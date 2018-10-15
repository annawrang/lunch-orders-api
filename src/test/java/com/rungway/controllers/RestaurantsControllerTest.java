package com.rungway.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rungway.domain.Order;
import com.rungway.domain.Restaurant;
import com.rungway.repositories.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(RestaurantsController.class)
public class RestaurantsControllerTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    protected RestaurantsController restaurantsController;

    @Mock
    protected RestaurantRepository repo;

    @Autowired
    private ObjectMapper jsonObjectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    private Restaurant spicyBasil;
    private Restaurant slugAndLettuce;

    @Before
    public void before() {
        jsonObjectMapper = new ObjectMapper();

        mvc = MockMvcBuilders.standaloneSetup(restaurantsController).build();
        spicyBasil = new Restaurant("spicy-basil", new ArrayList<>(Arrays.asList(
                new Order("Ana", "Pad Thai"),
                new Order("Chris", "Thai Red Curry")
        )));
        slugAndLettuce = new Restaurant("slug-and-lettuce", new ArrayList<>(Arrays.asList(
                new Order("Ana", "Veggie Burger"),
                new Order("Chris", "Greek salad")
        )));
    }

    @Test
    public void getOrders_valid() throws Exception {
        when(repo.findBySlug("spicy-basil")).thenReturn(Optional.of(spicyBasil));

        mvc.perform(get("/restaurants/spicy-basil/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Ana")))
                .andExpect(jsonPath("$[0].dish", is("Pad Thai")))
                .andExpect(jsonPath("$[1].name", is("Chris")))
                .andExpect(jsonPath("$[1].dish", is("Thai Red Curry")));
        verify(repo, times(1)).findBySlug("spicy-basil");
    }

    @Test
    public void getOrders_notFound() throws Exception {
        when(repo.findBySlug("woodys")).thenReturn(Optional.empty());

        mvc.perform(get("/restaurants/woodys/orders"))
                .andExpect(status().isNotFound());
        verify(repo, times(1)).findBySlug("woodys");
    }

    @Test
    public void getOrdersForPerson_valid() throws Exception {
        when(repo.findByOrders_Name("Ana")).thenReturn(new ArrayList<>(Arrays.asList(spicyBasil, slugAndLettuce)));

        mvc.perform(get("/restaurants/orders/Ana"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].name", is("Ana")))
                .andExpect(jsonPath("$[0].dish", is("Pad Thai")))
                .andExpect(jsonPath("$[1].name", is("Ana")))
                .andExpect(jsonPath("$[1].dish", is("Veggie Burger")));
        verify(repo, times(1)).findByOrders_Name("Ana");
    }

    @Test
    public void getOrdersForPerson_noOrders() throws Exception {
        when(repo.findByOrders_Name("Jack")).thenReturn(new ArrayList<>());

        mvc.perform(get("/restaurants/orders/Jack"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.spicy-basil").doesNotExist())
                .andExpect(jsonPath("$.slug-and-lettuce").doesNotExist());
        verify(repo, times(1)).findByOrders_Name("Jack");
    }

    @Test
    public void addRestaurant_valid() throws Exception {
        Restaurant newRestaurant = new Restaurant("bills", new ArrayList<>());

        when(repo.save(newRestaurant)).thenReturn(newRestaurant);

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(newRestaurant)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.slug", is("bills")));
        verify(repo, times(1)).save(newRestaurant);
    }

    @Test
    public void addOrder_valid() throws Exception {
        Order newOrder = new Order("Susan", "Pad Thai");
        when(repo.findBySlug("spicy-basil")).thenReturn(Optional.of(spicyBasil));
        when(repo.save(spicyBasil)).thenReturn(spicyBasil);

        mvc.perform(post("/restaurants/spicy-basil/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name", is("Susan")))
                .andExpect(jsonPath("$.dish", is("Pad Thai")));
        verify(repo, times(1)).findBySlug("spicy-basil");
        verify(repo, times(1)).save(spicyBasil);
    }

    @Test
    public void addOrder_restaurantNotFound() throws Exception {
        Order newOrder = new Order("Susan", "Pad Thai");
        when(repo.findBySlug("spicy-basil")).thenReturn(Optional.empty());

        mvc.perform(post("/restaurants/spicy-basil/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isNotFound());
        verify(repo, times(1)).findBySlug("spicy-basil");
    }

    @Test
    public void addOrder_orderAlreadyExists() throws Exception {
        Order newOrder = new Order("Ana", "Green Thai Curry");
        when(repo.findBySlug("spicy-basil")).thenReturn(Optional.of(spicyBasil));

        mvc.perform(post("/restaurants/spicy-basil/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isBadRequest());
        verify(repo, times(1)).findBySlug("spicy-basil");
    }

    @Test
    public void updateOrder_valid() throws Exception {
        Order newOrder = new Order("Ana", "Thai Green Curry");
        when(repo.findBySlug("spicy-basil")).thenReturn(Optional.of(spicyBasil));
        when(repo.save(spicyBasil)).thenReturn(spicyBasil);

        mvc.perform(put("/restaurants/spicy-basil/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Ana")))
                .andExpect(jsonPath("$.dish", is("Thai Green Curry")));
        verify(repo, times(1)).save(spicyBasil);
        verify(repo, times(1)).findBySlug("spicy-basil");
    }

    @Test
    public void updateOrder_restaurantDoesntExist() throws Exception {
        Order newOrder = new Order("Ana", "Thai Green Curry");
        when(repo.findBySlug("spicy-basil")).thenReturn(Optional.empty());

        mvc.perform(put("/restaurants/spicy-basil/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isNotFound());
        verify(repo, times(1)).findBySlug("spicy-basil");
        verify(repo, times(0)).save(spicyBasil);
    }

    @Test
    public void updateOrder_orderDoesntExist() throws Exception {
        Order newOrder = new Order("Jack", "Thai Green Curry");
        when(repo.findBySlug("spicy-basil")).thenReturn(Optional.of(spicyBasil));

        mvc.perform(put("/restaurants/spicy-basil/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isNotFound());
        verify(repo, times(1)).findBySlug("spicy-basil");
        verify(repo, times(0)).save(spicyBasil);
    }

}