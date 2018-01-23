package com.rungway.repositories;

import com.rungway.domain.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    public Restaurant findBySlug(String slug);

}
