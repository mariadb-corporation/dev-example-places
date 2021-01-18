package com.mariadb.places.repositories;

import com.mariadb.places.domain.Location;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    @Override
    @Query(value = "select id, name, type, longitude, latitude, " +
    "case when type = 'R' then concat((case when json_length(attr, '$.favorites') " +
    "is not null then json_length(attr, '$.favorites') else 0 end), ' favorite meals') " +
    "when type = 'A' then (case when json_value(attr, '$.lastVisitDate') is not null " +
    "then json_value(attr, '$.lastVisitDate') else 'N/A' end) " +
    "when type = 'S' then concat((case when json_length(attr, '$.events') is not null " +
    "then json_length(attr, '$.events') else 0 end), ' events') end as description, attr " +
    "from locations", nativeQuery = true)
    public Iterable<Location> findAll();

    @Modifying
    @Query(value = "update locations set attr = json_set(attr,'$.lastVisitDate', :lastVisitDate) where id = :id", nativeQuery = true)
    public void updateLastVisitDate(Integer id, String lastVisitDate);
}