package com.geolocation_service.repository;

import com.geolocation_service.entity.Geolocation;

import java.util.List;

public interface GeolocationRepo {
    List<Geolocation> getCitySuggestions(String query);
}
