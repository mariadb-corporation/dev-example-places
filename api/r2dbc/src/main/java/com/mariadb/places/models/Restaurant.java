package com.mariadb.places.models;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Data;

@Data
public class Restaurant {
    private String name;
    @Column("foodType")
    private String foodType;
    private String menu;
    private String favorites;
}