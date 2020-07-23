package com.mariadb.places.repositories;

import java.util.Optional;

import com.mariadb.places.domain.SportsVenue;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SportsVenueRepository extends CrudRepository<SportsVenue, Integer> {
    @Override
    @Query(value = "select " +
    "name, " +
    "json_value(attr,'$.details.yearOpened') as yearOpened, " +
    "json_value(attr,'$.details.capacity') as capacity, " +
    "json_query(attr,'$.events') as events " +
    "from locations " +
    "where id = :id", nativeQuery = true)
    public Optional<SportsVenue> findById(Integer id);

    @Modifying
    @Query(value = "update locations set attr = json_array_append(attr, '$.events', json_compact(:details)) where id = :id", nativeQuery = true)
    public void addEvent(Integer id, String details);
}