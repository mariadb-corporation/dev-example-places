package com.mariadb.places.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mariadb.places.models.Location;
import com.mariadb.places.models.Restaurant;
import com.mariadb.places.models.RestaurantFavorite;
import com.mariadb.places.models.SportsVenue;
import com.mariadb.places.models.SportsVenueEvent;
import com.mariadb.places.repositories.LocationsRepository;
import com.mariadb.places.repositories.RestaurantsRepository;
import com.mariadb.places.repositories.SportsVenuesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LocationsService {
    
    @Autowired
    private LocationsRepository locationsRepository;
    @Autowired
    private RestaurantsRepository restaurantsRepository;
    @Autowired
    private SportsVenuesRepository sportsVenuesRepository;

    public Boolean isValid(final Location location) {
        if (location != null && 
            !location.getName().isEmpty() &&
            !location.getDescription().isEmpty() &&
            !location.getType().isEmpty()) {
            return true;
        }
        return false;
    }

    public Flux<Location> getAllLocations() {
        return this.locationsRepository.findAll();
    }

    public Mono<Location> createLocation(final Location location) {
        return this.locationsRepository.save(location);
    }

    public Mono<Restaurant> getRestaurant(final Integer id) {
        return this.restaurantsRepository.findById(id);
    }

    public Mono<?> addRestaurantFavorite(RestaurantFavorite favorite) {
        return this.restaurantsRepository.addFavorite(favorite.getLocationId(), favorite.getDetails());
    }

    public Mono<SportsVenue> getSportsVenue(final Integer id) {
        return this.sportsVenuesRepository.findById(id);
    }

    public Mono<?> addSportsVenueEvent(SportsVenueEvent event) {
        return this.sportsVenuesRepository.addEvent(event.getLocationId(), event.getDetails());
    }

    public Mono<?> updateLastVisitDate(Integer id, Date lastVisitDate) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return this.locationsRepository.updateLastVisitDate(id, format.format(lastVisitDate));
    }
}