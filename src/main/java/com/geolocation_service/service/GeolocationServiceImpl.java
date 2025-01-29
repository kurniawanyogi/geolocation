package com.geolocation_service.service;

import com.geolocation_service.entity.Geolocation;
import com.geolocation_service.model.Suggestion;
import com.geolocation_service.repository.GeolocationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class GeolocationServiceImpl implements GeolocationService {
    private final GeolocationRepo geolocationRepo;

    @Override
    public List<Suggestion> getSuggestion(String query, Double latitude, Double longitude) {
        List<Geolocation> cities = geolocationRepo.getCitySuggestions(query);
        List<Suggestion> suggestions = new ArrayList<>();

        for (Geolocation city : cities) {
            double score = calculateScore(city, latitude, longitude);
            String suggestionName = city.getName() +
                    ", " +
                    city.getAdmin1Code() +
                    ", " +
                    new Locale("en", city.getCountryCode()).getDisplayCountry();

            Suggestion suggestion = new Suggestion();
            suggestion.setLatitude(city.getLatitude());
            suggestion.setLongitude(city.getLongitude());
            suggestion.setScore(score);
            suggestion.setName(suggestionName);
            suggestions.add(suggestion);
        }

        // Sortir berdasarkan skor (desc)
        suggestions.sort(Comparator.comparingDouble(Suggestion::getScore).reversed());
        return suggestions;
    }

    // Menghitung skor berdasarkan jarak dan kesamaan nama (distance-based scoring)
    private double calculateScore(Geolocation city, Double userLat, Double userLon) {
        double distance = 1.0;
        if (userLat == null && userLon == null) {
            // Tidak ada lokasi pada request, beri skor 1 (penuh)
            return distance;
        } else if (userLat == null) {
            distance = calculateDistance(city.getLatitude(), userLon, city.getLatitude(), city.getLongitude());
        } else if (userLon == null) {
            distance = calculateDistance(userLat, city.getLongitude(), city.getLatitude(), city.getLongitude());
        } else {
            distance = calculateDistance(userLat, userLon, city.getLatitude(), city.getLongitude());
        }
        // Skor dinormalisasi berdasarkan jarak, dan di round up dengan 1 angka di belakang desimal
        return roundUp(Math.max(0, 1 - (distance / 1000)), 1);
    }

    // Menghitung jarak menggunakan rumus Haversine
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Radius bumi dalam kilometer
        final double R = 6371.0;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        // Jarak dalam kilometer
        return R * c;
    }

    public double roundUp(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.ceil(value * scale) / scale;
    }
}