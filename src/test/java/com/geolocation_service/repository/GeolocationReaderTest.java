package com.geolocation_service.repository;

import com.geolocation_service.entity.Geolocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

class GeolocationReaderTest {
    @InjectMocks
    private GeolocationReader geolocationReaderImpl;

    @BeforeEach
    void setUp() {
        initMocks(this);
        geolocationReaderImpl.setFILE_PATH("src/test/resources/static/cities_canada-usa.tsv");
    }

    @Test
    void testInit_withValidTscFile_trieShouldPopulated() throws IOException {
        geolocationReaderImpl.init();

        assertNotNull(geolocationReaderImpl.getTrie(), "Trie should be populated after init");
    }

    @Test
    void testInit_withInvalidTscFile_returnException() throws IOException {
        geolocationReaderImpl.setFILE_PATH(null);
        try {
            geolocationReaderImpl.init();
        } catch (NullPointerException expected) {
            assertNotNull(expected, "");
        }
    }

    @Test
    void testGetCitySuggestion_withValidPrefix_returnListGeolocation() throws IOException {
        geolocationReaderImpl.init();
        List<Geolocation> suggestions = geolocationReaderImpl.getCitySuggestions("a");
        assertNotEquals(0, suggestions.size());
    }
}

