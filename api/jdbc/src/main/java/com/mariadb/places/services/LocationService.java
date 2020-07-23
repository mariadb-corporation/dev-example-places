package com.mariadb.places.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.mariadb.places.domain.Location;
import com.mariadb.places.domain.Restaurant;
import com.mariadb.places.domain.RestaurantFavorite;
import com.mariadb.places.domain.SportsVenue;
import com.mariadb.places.domain.SportsVenueEvent;
import com.mariadb.places.repositories.LocationRepository;
import com.mariadb.places.repositories.RestaurantRepository;
import com.mariadb.places.repositories.SportsVenueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private SportsVenueRepository sportsVenueRepository;

    public Boolean isValid(final Location location) {
        if (location != null && 
            !location.getName().isEmpty() &&
            !location.getDescription().isEmpty() &&
            !location.getType().isEmpty()) {
            return true;
        }
        return false;
    }

    public Iterable<Location> getAllLocations() {
        return this.locationRepository.findAll();
    }

    public Location createLocation(final Location location) {
        return this.locationRepository.save(location);
    }

    public Optional<Restaurant> getRestaurant(final Integer id) {
        return this.restaurantRepository.findById(id);
    }

    public void addRestaurantFavorite(RestaurantFavorite favorite) {
        this.restaurantRepository.addFavorite(favorite.getLocationId(), favorite.getDetails());
    }

    public Optional<SportsVenue> getSportsVenue(final Integer id) {
        return this.sportsVenueRepository.findById(id);
    }

    public void addSportsVenueEvent(SportsVenueEvent event) {
        this.sportsVenueRepository.addEvent(event.getLocationId(), event.getDetails());
    }

    public void updateLastVisitDate(Integer id, Date lastVisitDate) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        this.locationRepository.updateLastVisitDate(id, format.format(lastVisitDate));
    }
}