package com.mariadb.places.controllers;

import java.util.Date;
import java.util.Optional;

import com.mariadb.places.domain.Location;
import com.mariadb.places.domain.Restaurant;
import com.mariadb.places.domain.RestaurantFavorite;
import com.mariadb.places.domain.SportsVenue;
import com.mariadb.places.domain.SportsVenueEvent;
import com.mariadb.places.services.LocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService service;

    @GetMapping()
    public ResponseEntity<Iterable<Location>> get() {
        return ResponseEntity.ok(this.service.getAllLocations());
    }

    @PostMapping()
    public ResponseEntity<Location> post(@RequestBody Location location) {
        if (service.isValid(location)) {
            return ResponseEntity.ok(this.service.createLocation(location));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("restaurant")
    public ResponseEntity<Optional<Restaurant>> getRestaurant(@RequestParam Integer id) {
        return ResponseEntity.ok(this.service.getRestaurant(id));
    }

    @PostMapping("restaurant/favorites")
    public ResponseEntity<?> addRestaurantFavorite(@RequestBody RestaurantFavorite favorite) {
        if (favorite != null) {
            this.service.addRestaurantFavorite(favorite);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @GetMapping("sportsvenue")
    public ResponseEntity<Optional<SportsVenue>> getSportsVenue(@RequestParam Integer id) {
        return ResponseEntity.ok(this.service.getSportsVenue(id));
    }

    @PostMapping("sportsvenue/events")
    public ResponseEntity<?> addSportsVenueEvent(@RequestBody SportsVenueEvent event) {
        if (event != null) {
            this.service.addSportsVenueEvent(event);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @PutMapping("attractions")
    public ResponseEntity<?> updateLastVisitDate(@RequestParam Integer id, @RequestParam Date dt) {
        if (id > 0 && dt != null) {
            this.service.updateLastVisitDate(id, dt);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}