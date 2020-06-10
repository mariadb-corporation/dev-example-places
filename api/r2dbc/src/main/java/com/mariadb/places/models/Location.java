package com.mariadb.places.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table("locations")
public class Location {
    @Id private int id;
    private String name;
    private String description;
    private String type;
    private Double latitude;
    private Double longitude;
    private String attr;
}