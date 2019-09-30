package org.ndrianja.catmash;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CatmashRepository {

    private CatmashJson catmashJson;
    private Map<String, CatImage> catImageMap = new TreeMap<>(Comparator.reverseOrder());

    public CatmashRepository() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream file = getClass().getClassLoader().getResourceAsStream("cats.json");
        catmashJson = objectMapper.readValue(file, CatmashJson.class);
        catmashJson.getImages().forEach(c -> catImageMap.put(c.getId(), c));
    }

    public List<CatImage> getCatImageList() {
        return new ArrayList<>(catImageMap.values());
    }

    public CatImage[] selectCats(int count) {
        return catImageMap.values().stream().filter(c -> c.getQuota() > 0)
                .sorted(Comparator.comparingInt(CatImage::getQuota).reversed()
                        .thenComparing(Comparator.comparingInt(CatImage::getScore)))
                .limit(count).collect(Collectors.toList()).toArray(CatImage[]::new);
    }

    public TreeMap<Integer, List<CatImage>> getOrderedCatImageScores() {
        TreeMap<Integer, List<CatImage>> orderedCatScores = new TreeMap<>(Comparator.reverseOrder());
        catImageMap.forEach((k, v) -> {
            if (orderedCatScores.get(v.getScore()) == null) {
                orderedCatScores.put(v.getScore(), new ArrayList<CatImage>());
            }
            orderedCatScores.get(v.getScore()).add(v);
        });
        return orderedCatScores;
    }

    public CatImage getCatImage(String id) {
        return catImageMap.get(id);
    }

    public void incrementCatImageScore(String id) {
        CatImage x = catImageMap.get(id);
        x.incrementScore();
        catImageMap.replace(id, x);
    }

    public void decreaseQuota(String id) {
        CatImage x = catImageMap.get(id);
        x.decreaseQuota();
        catImageMap.replace(id, x);
    }
}
