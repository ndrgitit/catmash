package org.ndrianja.catmash;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CatmashService {

    private CatmashRepository catmashRepository;

    public CatmashRepository getCats() throws JsonParseException, JsonMappingException, IOException {
        if (catmashRepository == null) {
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target("https://latelier.co/data/cats.json");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            ObjectMapper objectMapper = new ObjectMapper();
            return catmashRepository = objectMapper.readValue(response.readEntity(String.class),
                    CatmashRepository.class);
        } else {
            return catmashRepository;
        }
    }

    public void setCats(CatmashRepository cats) {
        this.catmashRepository = cats;
    }
}
