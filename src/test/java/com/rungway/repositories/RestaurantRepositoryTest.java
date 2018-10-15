package com.rungway.repositories;

import com.rungway.domain.Order;
import com.rungway.domain.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository repository;

    private Restaurant testRestaurant1;
    private Restaurant testRestaurant2;

    @Before
    public void before() {
        testRestaurant1 = new Restaurant("testRestaurant1", new ArrayList<>(Arrays.asList(
                new Order("TestPerson1", "Falafel"),
                new Order("TestPerson2", "Burger")
        )));
        testRestaurant2 = new Restaurant("testRestaurant2", new ArrayList<>(Arrays.asList(
                new Order("TestPerson1", "Veggie Burger"),
                new Order("TestPerson2", "Greek salad")
        )));
    }

    @Test
    public void getRestaurants_valid() {
        repository.insert(testRestaurant1);

        Optional<Restaurant> restaurant = repository.findBySlug("testRestaurant1");

        assertNotNull(restaurant);
        assertEquals(true, restaurant.isPresent());
        assertEquals(2, restaurant.get().getOrders().size());
        assertEquals("testRestaurant1", restaurant.get().getSlug());
        assertEquals("TestPerson1", restaurant.get().getOrders().get(0).getName());
        assertEquals("Falafel", restaurant.get().getOrders().get(0).getDish());
        assertEquals("TestPerson2", restaurant.get().getOrders().get(1).getName());
        assertEquals("Burger", restaurant.get().getOrders().get(1).getDish());
    }

    @Test
    public void getRestaurants_notPresent() {
        Optional<Restaurant> restaurant = repository.findBySlug("nonExistentRestaurant");

        assertNotNull(restaurant);
        assertEquals(false, restaurant.isPresent());
    }

    @Test
    public void findByOrdersName_valid() {
        repository.insert(testRestaurant1);
        repository.insert(testRestaurant2);

        List<Restaurant> restaurants = repository.findByOrders_Name("TestPerson1");

        assertEquals(2, restaurants.size());
        assertEquals("testRestaurant1", restaurants.get(0).getSlug());
        assertEquals("testRestaurant2", restaurants.get(1).getSlug());
    }

    @Test
    public void findByOrdersName_noOrders() {
        List<Restaurant> restaurants = repository.findByOrders_Name("TestPersonNoOrders");

        assertEquals(0, restaurants.size());
    }

    @After
    public void after() {
        repository.delete(testRestaurant1);
        repository.delete(testRestaurant2);
    }
}
