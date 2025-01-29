package com.geolocation_service.service;

import com.geolocation_service.model.Suggestion;

import java.util.List;

public interface GeolocationService {
    List<Suggestion> getSuggestion(String location, Double latitude, Double longitude);
}
