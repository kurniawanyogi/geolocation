package com.geolocation_service.service;

import com.geolocation_service.entity.Geolocation;
import com.geolocation_service.model.Suggestion;
import com.geolocation_service.repository.GeolocationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


class GeolocationServiceImplTest {

    @Mock
    private GeolocationRepo geolocationRepo;

    @InjectMocks
    private GeolocationServiceImpl geolocationServiceImpl;

    private Geolocation geo1, geo2, geo3;

    @BeforeEach
    void setUp() {
        initMocks(this);
        geo1 = new Geolocation(1, "London", 51.5074, -0.1278, "GB", "ENG");
        geo2 = new Geolocation(2, "London", 42.9834, -81.2330, "CA", "ON");
        geo3 = new Geolocation(3, "Londontown", 38.9334, -76.5494, "US", "MD");
    }

    @Test
    void testGetSuggestion_withNullLatitudeLongitude_returnsSuggestionList() {
        when(geolocationRepo.getCitySuggestions("Lon")).thenReturn(Arrays.asList(geo1, geo2, geo3));

        List<Suggestion> suggestions = geolocationServiceImpl.getSuggestion("Lon", null, null);

        assertNotNull(suggestions);
        assertEquals(3, suggestions.size(), "Should return 3 suggestions for 'Lon'");
        assertEquals("London, ENG, United Kingdom", suggestions.get(0).getName(), "First suggestion should be 'London, GB'");
        assertEquals(1, suggestions.get(0).getScore(), "Should return 1 because null latitude and longitude");
    }

    @Test
    void testGetSuggestion_withValidLatitudeLongitude_returnsSuggestionList() {
        when(geolocationRepo.getCitySuggestions("Lon")).thenReturn(Arrays.asList(geo1, geo2, geo3));

        List<Suggestion> suggestions = geolocationServiceImpl.getSuggestion("Lon", 43.70011, -79.4163);

        assertNotNull(suggestions);
        assertEquals(3, suggestions.size(), "Should return 3 suggestions for 'Lon'");
        assertEquals("London, ON, Canada", suggestions.get(0).getName(), "First suggestion should be 'London, ON, Canada");
        assertNotEquals(1, suggestions.get(0).getScore(), "Should return < 1 because latitude and longitude not exactly same");
    }
}

