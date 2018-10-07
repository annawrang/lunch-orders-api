package com.rungway;

import com.rungway.domain.Order;
import com.rungway.domain.Restaurant;
import com.rungway.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class LunchOrdersApplication {

    @Autowired
    private RestaurantRepository repo;

    @EventListener(ApplicationReadyEvent.class)
    public void bootstrapData() {

        List<Order> orders = new ArrayList<>(Arrays.asList(
                new Order("Endre", "Thai Green Curry"),
                new Order("Rohith", "Spicy Chicken Pad Thai"),
                new Order("Ana", "Prawn Pad Thai"),
                new Order("HarryD", "Chilli Chicken Fried Rice")));


        repo.save(new Restaurant(
                "spicy-basil",
                orders
        ));
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LunchOrdersApplication.class, args);
    }

}
