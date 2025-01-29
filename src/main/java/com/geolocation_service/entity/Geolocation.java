package com.geolocation_service.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Geolocation {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String countryCode;
    private String admin1Code;
}
