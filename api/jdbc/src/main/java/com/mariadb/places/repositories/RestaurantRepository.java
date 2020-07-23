package com.mariadb.places.repositories;

import java.util.Optional;

import com.mariadb.places.domain.Restaurant;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    @Override
    @Query(value = "select " +
    "name, " +
    "json_value(attr,'$.details.foodType') as foodType, " +
    "json_value(attr,'$.details.menu') as menu, " +
    "json_query(attr,'$.favorites') as favorites " +
    "from locations " +
    "where id = :id", nativeQuery = true)
    public Optional<Restaurant> findById(Integer id);

    @Modifying
    @Query(value = "update locations set attr = json_array_append(attr, '$.favorites', json_compact(:details)) where id = :id", nativeQuery = true)
    public void addFavorite(Integer id, String details);
}