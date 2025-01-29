package com.geolocation_service.util;

import com.geolocation_service.entity.Geolocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
    private TrieNode root = new TrieNode();

    // Menyisipkan kata dan lokasi ke dalam Trie
    public void insert(String word, Geolocation location) {
        // Untuk support case insensitive
        word = word.toLowerCase();
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isEndOfWord = true;
        node.geoLocations.add(location);
    }

    // Mencari semua kata yang dimulai dengan prefix tertentu
    public List<Geolocation> searchByPrefix(String prefix) {
        // Untuk support case insensitive
        prefix = prefix.toLowerCase();
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return new ArrayList<>();
            }
            node = node.children.get(c);
        }
        return collectAllLocations(node);
    }

    // Mengumpulkan semua lokasi yang terkait dengan prefix
    private List<Geolocation> collectAllLocations(TrieNode node) {
        List<Geolocation> result = new ArrayList<>();
        if (node.isEndOfWord) {
            result.addAll(node.geoLocations);
        }
        for (TrieNode child : node.children.values()) {
            result.addAll(collectAllLocations(child));
        }
        return result;
    }

    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;
        List<Geolocation> geoLocations = new ArrayList<>();
    }
}
