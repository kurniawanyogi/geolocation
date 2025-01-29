package com.geolocation_service.util;

import com.geolocation_service.entity.Geolocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrieTest {

    private Trie trie;

    @BeforeEach
    void setUp() {
        trie = new Trie();
    }

    @Test
    void testInsert_withValidData_returnsListGeolocation() {
        Geolocation geo1 = new Geolocation(1, "London", 51.5074, -0.1278, "GB", "ENG");
        Geolocation geo2 = new Geolocation(2, "London", 42.9834, -81.2330, "CA", "ON");
        Geolocation geo3 = new Geolocation(3, "Londontown", 38.9334, -76.5494, "US", "MD");

        trie.insert("London", geo1);
        trie.insert("London", geo2);
        trie.insert("Londontown", geo3);

        List<Geolocation> results = trie.searchByPrefix("Lon");

        assertEquals(3, results.size(), "Should return 3 cities matching the prefix 'Lon'");
        assertTrue(results.contains(geo1), "Should contain London, GB");
        assertTrue(results.contains(geo2), "Should contain London, CA");
        assertTrue(results.contains(geo3), "Should contain Londontown, US");
    }

    @Test
    void testSearchByPrefix_withLowerCasePrefix_returnsListGeolocation() {
        Geolocation geo1 = new Geolocation(1, "London", 51.5074, -0.1278, "GB", "ENG");
        Geolocation geo2 = new Geolocation(2, "London", 42.9834, -81.2330, "CA", "ON");
        Geolocation geo3 = new Geolocation(3, "Londontown", 38.9334, -76.5494, "US", "MD");

        trie.insert("London", geo1);
        trie.insert("London", geo2);
        trie.insert("Londontown", geo3);

        List<Geolocation> results = trie.searchByPrefix("lon");

        assertEquals(3, results.size(), "Should return 3 cities matching the prefix 'Lon'");
        assertTrue(results.contains(geo1), "Should contain London, GB");
        assertTrue(results.contains(geo2), "Should contain London, CA");
        assertTrue(results.contains(geo3), "Should contain Londontown, US");
    }

    @Test
    void testSearchByPrefix_withUpperCasePrefix_returnsListGeolocation() {
        Geolocation geo1 = new Geolocation(1, "London", 51.5074, -0.1278, "GB", "ENG");
        Geolocation geo2 = new Geolocation(2, "London", 42.9834, -81.2330, "CA", "ON");
        Geolocation geo3 = new Geolocation(3, "Londontown", 38.9334, -76.5494, "US", "MD");

        trie.insert("London", geo1);
        trie.insert("London", geo2);
        trie.insert("Londontown", geo3);

        List<Geolocation> results = trie.searchByPrefix("LON");

        assertEquals(3, results.size(), "Should return 3 cities matching the prefix 'Lon'");
        assertTrue(results.contains(geo1), "Should contain London, GB");
        assertTrue(results.contains(geo2), "Should contain London, CA");
        assertTrue(results.contains(geo3), "Should contain Londontown, US");
    }

    @Test
    void testSearchByPrefix_withInvalidPrefix_returnsEmptyList() {
        List<Geolocation> results = trie.searchByPrefix("a");

        assertTrue(results.isEmpty(), "Should return no results for prefix 'a'");
    }
}

