package com.rungway.controllers;

import com.rungway.domain.Order;
import com.rungway.domain.Restaurant;
import com.rungway.exceptions.BadRequestException;
import com.rungway.exceptions.NotFoundException;
import com.rungway.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantRepository repo;


    @GetMapping("/{slug}/orders")
    public List<Order> getOrders(@PathVariable String slug) throws NotFoundException {
        Restaurant restaurant = repo.findBySlug(slug).orElseThrow(NotFoundException::new);
        return restaurant.getOrders();
    }

    @GetMapping("/orders/{name}")
    public List<Order> getOrdersForPerson(@PathVariable String name) {
        return repo.findByOrders_Name(name).stream()
                .flatMap(r -> r.getOrders().stream())
                .filter(o -> o.getName().equals(name)).collect(Collectors.toList());
    }

    @PostMapping
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        repo.save(restaurant);
        return restaurant;
    }

    @PostMapping("/{slug}/orders")
    public Order addOrder(@PathVariable String slug, @RequestBody Order order) throws NotFoundException, BadRequestException {
        Restaurant restaurant = repo.findBySlug(slug).orElseThrow(NotFoundException::new);

        if (restaurant.getOrders().stream().noneMatch(o -> o.getName().equalsIgnoreCase(order.getName()))) {
            restaurant.getOrders().add(order);
        } else {
            throw new BadRequestException();
        }
        repo.save(restaurant);
        return order;
    }

    @PutMapping("/{slug}/orders")
    public Order updateOrder(@PathVariable String slug, @RequestBody Order order) throws NotFoundException {
        Restaurant restaurant = repo.findBySlug(slug).orElseThrow(NotFoundException::new);

        if (restaurant.getOrders().stream().noneMatch(o -> o.getName().equalsIgnoreCase(order.getName()))) {
            throw new NotFoundException();
        } else {
            restaurant.getOrders().stream()
                    .filter(o -> o.getName().equalsIgnoreCase(order.getName())).findAny().get().setDish(order.getDish());
        }
        repo.save(restaurant);
        return order;
    }


}
