package com.mariadb.places.repositories;

import com.mariadb.places.models.SportsVenue;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface SportsVenuesRepository extends ReactiveCrudRepository<SportsVenue, Integer> {
    @Override
    @Query("select " +
    "name, " +
    "json_value(attr,'$.details.yearOpened') as yearOpened, " +
    "json_value(attr,'$.details.capacity') as capacity, " +
    "json_query(attr,'$.events') as events " +
    "from locations " +
    "where id = :id")
    public Mono<SportsVenue> findById(Integer id);

    @Query("update locations set attr = json_array_append(attr, '$.events', json_compact(:details)) where id = :id")
    public Mono<?> addEvent(Integer id, String details);
}