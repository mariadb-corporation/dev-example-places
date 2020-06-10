package com.mariadb.places.repositories;

import com.mariadb.places.models.Restaurant;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface RestaurantsRepository extends ReactiveCrudRepository<Restaurant, Integer> {
    @Override
    @Query("select " +
    "name, " +
    "json_value(attr,'$.details.foodType') as foodType, " +
    "json_value(attr,'$.details.menu') as menu, " +
    "json_query(attr,'$.favorites') as favorites " +
    "from locations " +
    "where id = :id")
    public Mono<Restaurant> findById(Integer id);

    @Query("update locations set attr = json_array_append(attr, '$.favorites', json_compact(:details)) where id = :id")
    public Mono<?> addFavorite(Integer id, String details);
}