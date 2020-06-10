package com.mariadb.places.models;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Data;

@Data
public class SportsVenue {
    private String name;
    @Column("yearOpened")
    private int yearOpened;
    private int capacity;
    private String events;
}