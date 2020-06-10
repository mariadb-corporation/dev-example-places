package com.mariadb.places.models;

import lombok.Data;

@Data
public class RestaurantFavorite {
    private Integer locationId;
    private String details;
}