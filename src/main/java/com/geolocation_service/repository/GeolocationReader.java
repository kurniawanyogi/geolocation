package com.geolocation_service.repository;

import com.geolocation_service.entity.Geolocation;
import com.geolocation_service.util.Trie;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@Service
public class GeolocationReader implements GeolocationRepo {
    @Getter
    private final Trie trie = new Trie();
    @Setter
    @Value("${file.cities-canada-usa:static/cities_canada-usa.tsv}")
    private String FILE_PATH;

    @PostConstruct
    public void init() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH);

        if (inputStream == null) {
            throw new FileNotFoundException("File tidak ditemukan di dalam JAR");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT
                    .withDelimiter('\t')
                    .withQuote(null)
                    .withFirstRecordAsHeader()
                    .parse(reader);
            for (CSVRecord record : csvRecords) {
                Geolocation geolocation = new Geolocation();
                geolocation.setId(Integer.parseInt(record.get("id")));
                geolocation.setName(record.get("name"));
                geolocation.setLongitude(Double.parseDouble(record.get("long")));
                geolocation.setLatitude(Double.parseDouble(record.get("lat")));
                geolocation.setCountryCode(record.get("country"));
                geolocation.setAdmin1Code(record.get("admin1"));
                trie.insert(record.get("name"), geolocation);
            }
        }
    }

    @Override
    public List<Geolocation> getCitySuggestions(String query) {
        return trie.searchByPrefix(query);
    }
}
