package com.rungway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import com.rungway.domain.Restaurant;
import com.rungway.repositories.RestaurantRepository;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LunchOrdersApplication {

    @Autowired
    private RestaurantRepository repo;

    @EventListener(ApplicationReadyEvent.class)
    public void bootstrapData() {
        Map<String, String> orders = new HashMap<>();
        orders.put("Endre", "Thai Green Curry");
        orders.put("Rohith", "Spidy Chicken Pad Thai");
        orders.put("Ana", "Prawn Pad Thai");
        orders.put("HarryD", "Chilli Chicken Fried Rice");
        
        repo.save(new Restaurant(
            "spicy-basil",
            orders
        ));
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LunchOrdersApplication.class, args);
    }

}
