package com.mariadb.places.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity()
@Table(name = "locations")
@NoArgsConstructor
public class Location {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull private Integer id;
    @NonNull private String name;
    @NonNull private String description;
    @NonNull private String type;
    @NonNull private Double latitude;
    @NonNull private Double longitude;
    private String attr;
}