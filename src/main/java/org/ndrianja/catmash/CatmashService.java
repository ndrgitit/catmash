package org.ndrianja.catmash;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CatmashService {

    private CatmashRepository catmashRepository;

    public CatmashRepository getCats() {
        if (catmashRepository == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                InputStream file = getClass().getClassLoader().getResourceAsStream("cats.json");
                return catmashRepository = objectMapper.readValue(file, CatmashRepository.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return catmashRepository;
        }
    }

    public List<CatImage> getImages() {
        return getCats().getImages();
    }

    public CatImage[] selectTwoCats() {
        return getCats().selectCats(2);
    }

    public TreeMap<Integer, List<CatImage>> getOrderedCatScores()
            throws JsonParseException, JsonMappingException, IOException {
        return getCats().getOrderedCatScores();
    }
}
