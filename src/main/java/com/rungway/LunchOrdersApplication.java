package com.rungway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.rungway.domain.Restaurant;
import com.rungway.repositories.RestaurantRepository;
import java.util.HashMap;

@SpringBootApplication
public class LunchOrdersApplication {

    @Autowired
    private RestaurantRepository repo;

    @EventListener(ApplicationReadyEvent.class)
    public void bootstrapData() {
        HashMap<String, String> orders = new HashMap<String, String>();
        orders.put("Endre", "Thai Green Curry");
        orders.put("Rohith", "Spidy Chicken Pad Thai");
        orders.put("Ana", "Prawn Pad Thai");
        orders.put("Harry D", "Chilli Chicken Fried Rice");
        
        repo.save(new Restaurant(
            "spicy-basil",
            orders
        ));
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LunchOrdersApplication.class, args);
    }

}
