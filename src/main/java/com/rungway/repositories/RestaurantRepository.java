package com.rungway.repositories;

import com.rungway.domain.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    Restaurant findBySlug(String slug);

    List<Restaurant> findByOrders_Name(String name);
}
