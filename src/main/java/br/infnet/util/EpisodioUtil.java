package br.infnet.util;

import br.infnet.exception.EpisodioNotFoundException;
import br.infnet.model.Episodio;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EpisodioUtil {
    public Episodio getById(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI("https://rickandmortyapi.com/api/episode/" + id))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 404){
                throw new EpisodioNotFoundException("Not Found");
            }
            //ObjectMapper objectMapper = new ObjectMapper();
            ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
            Episodio episodio = objectMapper.readValue(response.body(), Episodio.class);

            return episodio;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}