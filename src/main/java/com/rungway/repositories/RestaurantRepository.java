package com.rungway.repositories;

import com.rungway.domain.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    Optional<Restaurant> findBySlug(String slug);

    List<Restaurant> findByOrders_Name(String name);
}
