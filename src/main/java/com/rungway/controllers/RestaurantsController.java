package com.rungway.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    @GetMapping("/{restaurant}/orders")
    @ResponseBody
    Map<String, String> orders(@PathVariable String restaurant) {
        HashMap<String, String> orders = new HashMap<String, String>();
        orders.put("Endre", "Thai Green Curry");
        orders.put("Rohith", "Spidy Chicken Pad Thai");
        orders.put("Ana", "Prawn Pad Thai");
        orders.put("Harry D", "Chilli Chicken Fried Rice");

        return orders;
    }

}
