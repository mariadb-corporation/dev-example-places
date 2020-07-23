package com.mariadb.places.domain;

import javax.persistence.*;

import lombok.Data;

@Data
@Embeddable
public class SportsVenueEvent {
    private Integer locationId;
    private String details;
}