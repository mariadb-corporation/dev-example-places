package com.mariadb.places.controllers;

import java.util.Date;

import com.mariadb.places.models.Location;
import com.mariadb.places.models.Restaurant;
import com.mariadb.places.models.RestaurantFavorite;
import com.mariadb.places.models.SportsVenue;
import com.mariadb.places.models.SportsVenueEvent;
import com.mariadb.places.services.LocationsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/locations")
public class LocationsController {

    @Autowired
    private LocationsService service;

    @GetMapping()
    public ResponseEntity<Flux<Location>> get() {
        return ResponseEntity.ok(this.service.getAllLocations());
    }

    @PostMapping()
    public ResponseEntity<Mono<Location>> post(@RequestBody Location location) {
        if (service.isValid(location)) {
            return ResponseEntity.ok(this.service.createLocation(location));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("restaurant")
    public ResponseEntity<Mono<Restaurant>> getRestaurant(@RequestParam Integer id) {
        return ResponseEntity.ok(this.service.getRestaurant(id));
    }

    @PostMapping("restaurant/favorites")
    public ResponseEntity<Mono<?>> addRestaurantFavorite(@RequestBody RestaurantFavorite favorite) {
        return ResponseEntity.ok(this.service.addRestaurantFavorite(favorite));
    }

    @GetMapping("sportsvenue")
    public ResponseEntity<Mono<SportsVenue>> getSportsVenue(@RequestParam Integer id) {
        return ResponseEntity.ok(this.service.getSportsVenue(id));
    }

    @PostMapping("sportsvenue/events")
    public ResponseEntity<Mono<?>> addSportsVenueEvent(@RequestBody SportsVenueEvent event) {
        return ResponseEntity.ok(this.service.addSportsVenueEvent(event));
    }

    @PutMapping("attractions")
    public ResponseEntity<?> updateLastVisitDate(@RequestParam Integer id, @RequestParam Date dt) {
        return ResponseEntity.ok(this.service.updateLastVisitDate(id, dt));
    }
}