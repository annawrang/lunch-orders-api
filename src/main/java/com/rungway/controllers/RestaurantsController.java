package com.rungway.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.rungway.domain.Restaurant;
import com.rungway.exceptions.NotFoundException;
import com.rungway.repositories.RestaurantRepository;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantRepository repo;

    @GetMapping("/{slug}/orders")
    @ResponseBody
    Map<String, String> orders(@PathVariable String slug) throws NotFoundException {
        Restaurant restaurant = Optional.ofNullable(repo.findBySlug(slug)).orElseThrow(NotFoundException::new);
        return restaurant.getOrders();
    }

}
