package com.mariadb.places.repositories;

import java.util.Date;

import com.mariadb.places.models.Location;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LocationsRepository extends ReactiveCrudRepository<Location, Integer> {
    @Override
    @Query("select id, name, type, longitude, latitude, " +
    "case when type = 'R' then concat((case when json_length(attr, '$.favorites') " +
    "is not null then json_length(attr, '$.favorites') else 0 end), ' favorite meals') " +
    "when type = 'A' then (case when json_value(attr, '$.lastVisitDate') is not null " +
    "then json_value(attr, '$.lastVisitDate') else 'N/A' end) " +
    "when type = 'S' then concat((case when json_length(attr, '$.events') is not null " +
    "then json_length(attr, '$.events') else 0 end), ' events') end as description " +
    "from locations")
    public Flux<Location> findAll();

    @Query("update locations set attr = json_set(attr,'$.lastVisitDate', :lastVisitDate) where id = :id")
    public Mono<?> updateLastVisitDate(Integer id, String lastVisitDate);
}