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
            /*
             * Client client = ClientBuilder.newClient(); WebTarget webTarget =
             * client.target("https://latelier.co/data/cats.json"); Invocation.Builder invocationBuilder =
             * webTarget.request(MediaType.APPLICATION_JSON); Response response = invocationBuilder.get();
             */
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                InputStream file = getClass().getClassLoader().getResourceAsStream("cats.json");
                return catmashRepository = objectMapper.readValue(file, CatmashRepository.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return catmashRepository;
        }
    }

    public void setCats(CatmashRepository cats) {
        this.catmashRepository = cats;
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
