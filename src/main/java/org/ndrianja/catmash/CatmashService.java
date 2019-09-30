package org.ndrianja.catmash;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class CatmashService {

    @Autowired
    private CatmashRepository catmashRepository;

    public List<CatImage> getCatImages() throws JsonParseException, JsonMappingException, IOException {
        return catmashRepository.getCatImageList();
    }

    public CatImage[] selectFiveCatImage() throws JsonParseException, JsonMappingException, IOException {
        CatImage[] x = catmashRepository.selectCats(5);
        catmashRepository.decreaseQuota(x[0].getId());
        catmashRepository.decreaseQuota(x[1].getId());
        return x;
    }

    public TreeMap<Integer, List<CatImage>> getOrderedCatImageScores()
            throws JsonParseException, JsonMappingException, IOException {
        return catmashRepository.getOrderedCatImageScores();
    }

    public CatImage getCatImageById(String id) throws JsonParseException, JsonMappingException, IOException {
        return catmashRepository.getCatImage(id);
    }

    public void incrementScore(String id) throws JsonParseException, JsonMappingException, IOException {
        catmashRepository.incrementCatImageScore(id);
    }

}
