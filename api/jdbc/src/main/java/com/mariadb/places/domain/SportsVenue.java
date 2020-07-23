package com.mariadb.places.domain;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class SportsVenue {
    @Id 
    @GeneratedValue
    private long id;
    private String name;
    @Column(name = "yearOpened")
    private int yearOpened;
    private int capacity;
    private String events;
}