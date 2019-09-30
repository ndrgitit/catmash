package org.ndrianja.catmash;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CatmashRepository {

    private CatmashJson catmashJson;
    private List<CatImage> catImageList;

    public CatmashRepository() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream file = getClass().getClassLoader().getResourceAsStream("cats.json");
        catmashJson = objectMapper.readValue(file, CatmashJson.class);
        catImageList = catmashJson.getImages();
    }

    public List<CatImage> getCatImageList() {
        return catImageList;
    }

    public CatImage[] selectCats(int count) {
        return catImageList.stream()
                .sorted(Comparator.comparingInt(CatImage::getQuota)
                        .thenComparing(Comparator.comparingInt(CatImage::getScore)))
                .limit(count).collect(Collectors.toList()).toArray(CatImage[]::new);
    }

    public TreeMap<Integer, List<CatImage>> getOrderedCatImageScores() {
        TreeMap<Integer, List<CatImage>> orderedCatScores = new TreeMap<>((a, b) -> {
            if (a < b) {
                return 1;
            } else if (a > b) {
                return -1;
            } else {
                return 0;
            }
        });
        for (CatImage catImage : catImageList) {
            if (orderedCatScores.get(catImage.getScore()) == null) {
                orderedCatScores.put(catImage.getScore(), new ArrayList<CatImage>());
            }
            orderedCatScores.get(catImage.getScore()).add(catImage);
        }
        return orderedCatScores;
    }

    public CatImage getCatImage(String id) {
        for (CatImage cat : catImageList) {
            if (cat.getId().equals(id)) {
                return cat;
            }
        }
        return null;
    }

    public void incrementCatImageScore(String id) {
        getCatImage(id).incrementScore();
    }

    @Override
    public String toString() {
        StringBuilder resp = new StringBuilder();
        for (CatImage cat : catmashJson.getImages()) {
            resp.append(cat.toString() + "\n");
        }
        return resp.toString();
    }
}
