package com.geolocation_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Suggestion {
    private String name;
    private double latitude;
    private double longitude;
    private double score;
}
