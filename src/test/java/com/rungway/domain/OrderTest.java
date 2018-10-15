package com.rungway.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void creatOrder_valid() {
        Order order = new Order("Ana", "Falafel");

        assertNotNull(order);
        assertEquals("Ana", order.getName());
        assertEquals("Falafel", order.getDish());
    }

    @Test(expected = NullPointerException.class)
    public void creatOrder_nullName() {
        Order order = new Order(null, "Falafel");

        assertNull(order);
    }

    @Test(expected = NullPointerException.class)
    public void creatOrder_nullDish() {
        Order order = new Order("Ana", null);

        assertNull(order);
    }

    @Test(expected = NullPointerException.class)
    public void creatOrder_nullParams() {
        Order order = new Order(null, null);

        assertNull(order);
    }
}
