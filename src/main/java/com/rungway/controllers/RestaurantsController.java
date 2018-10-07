package com.rungway.controllers;

import com.rungway.domain.Order;
import com.rungway.domain.Restaurant;
import com.rungway.exceptions.NotFoundException;
import com.rungway.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantRepository repo;


    @GetMapping("/{slug}/orders")
    public List<Order> orders(@PathVariable String slug) throws NotFoundException {
        Restaurant restaurant = Optional.ofNullable(repo.findBySlug(slug)).orElseThrow(NotFoundException::new);
        return restaurant.getOrders();
    }

    @GetMapping("/orders/{name}")
    public Map<String, Order> getOrdersForPerson(@PathVariable String name) {
        Map<String, Order> orders = new HashMap<>();
        for (Restaurant r : repo.findByOrders_Name(name)) {
            int index = -1;
            for (int i = 0; i < r.getOrders().size(); i++) {
                if (r.getOrders().get(i).getName().equalsIgnoreCase(name)) {
                    index = i;
                }
            }
            orders.put(r.getSlug(), r.getOrders().get(index));
        }
        return orders;
    }


    @PutMapping("/{slug}/orders")
    public Order updateOrder(@PathVariable String slug, @RequestBody Order order) {
        Restaurant restaurant = repo.findBySlug(slug);
        if (restaurant != null) {
            if (restaurant.getOrders().stream().noneMatch(o -> o.getName().equalsIgnoreCase(order.getName()))) {
                restaurant.getOrders().add(order);
            } else {
                restaurant.getOrders().stream()
                        .filter(o -> o.getName().equalsIgnoreCase(order.getName())).findAny().get().setDish(order.getDish());
            }
        } else {
            restaurant = new Restaurant(slug, new ArrayList<>(Arrays.asList(order)));
        }
        repo.save(restaurant);
        return order;
    }


}
