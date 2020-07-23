package com.mariadb.places.domain;

import javax.persistence.*;

import lombok.Data;

@Data
@Embeddable
public class RestaurantFavorite {
    private Integer locationId;
    private String details;
}