package com.soohwang.bluebuddy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seatemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double lat;
    private double lng;

    @Column(nullable = false)
    private double temp;

    private String time;

    public Seatemp(double lat, double lng, double temp, String time) {
        this.lat = lat;
        this.lng = lng;
        this.temp = temp;
        this.time = time;
    }


}
