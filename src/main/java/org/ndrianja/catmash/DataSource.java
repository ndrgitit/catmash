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

public class DataSource {

    private CatImagesSet cats;

    public CatImagesSet getCats() throws JsonParseException, JsonMappingException, IOException {
        if (cats == null) {
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target("https://latelier.co/data/cats.json");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            ObjectMapper objectMapper = new ObjectMapper();
            return cats = objectMapper.readValue(response.readEntity(String.class), CatImagesSet.class);
        } else {
            return cats;
        }

    }

    public void setCats(CatImagesSet cats) {
        this.cats = cats;
    }
}
