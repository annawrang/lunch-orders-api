package com.rungway.domain;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RestaurantTest {

    @Test
    public void createRestaurant_valid() {
        Restaurant restaurant = new Restaurant("slug", new ArrayList<>());

        assertNotNull(restaurant);
        assertEquals("slug", restaurant.getSlug());
        assertEquals(0, restaurant.getOrders().size());
    }

    @Test(expected = NullPointerException.class)
    public void createRestaurant_nullOrders() {
        Restaurant restaurant = new Restaurant("slug", null);

        assertNull(restaurant);
    }

    @Test(expected = NullPointerException.class)
    public void createRestaurant_nullSlug() {
        Restaurant restaurant = new Restaurant(null, new ArrayList<>());

        assertNull(restaurant);
    }

    @Test(expected = NullPointerException.class)
    public void createRestaurant_nullParams() {
        Restaurant restaurant = new Restaurant(null, null);

        assertNull(restaurant);
    }
}
