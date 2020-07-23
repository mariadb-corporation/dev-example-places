package com.mariadb.places.domain;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class Restaurant {
    @Id 
    @GeneratedValue
    private long id;
    private String name;
    @Column(name = "foodType")
    private String foodType;
    private String menu;
    private String favorites;
}