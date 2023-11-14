package br.infnet.util;

import br.infnet.exception.EpisodioNotFoundException;
import br.infnet.exception.PersonagemNotFoundException;
import br.infnet.model.Episodio;
import br.infnet.model.PayloadPersonagemList;
import br.infnet.model.Personagem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PersonagemUtil {
    public Personagem getPersonagem(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI("https://rickandmortyapi.com/api/character/" + id))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 404){
                throw new PersonagemNotFoundException("Personagem não localizado");
            }
            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

            Personagem personagem = mapper.readValue(response.body(), Personagem.class);
            return personagem;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public Personagem getPersonagem(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI(url))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 404){
                throw new PersonagemNotFoundException("Personagem não localizado");
            }
            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

            Personagem personagem = mapper.readValue(response.body(), Personagem.class);
            return personagem;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Personagem> getPersonagens(List<String> personagens) {
        List<Personagem> personagensList = new ArrayList<>();
        /*
        for(String url: personagens){
            Personagem personagem = getPersonagem(url);
            personagensList.add(personagem);
        }

         */
        //personagens.stream().map(url -> getPersonagem(url)).toList();
        return personagens.stream().map(this::getPersonagem).toList();
    }
    public List<Personagem> getPersonagens(List<String> personagens, int size) {
        return personagens.subList(0,size).stream().map(this::getPersonagem).toList();
    }
    public void getAvatar(Personagem personagem){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI(personagem.getImage())).build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            String fileName = "avatar/" + personagem.getName() + ".png";
            Files.write(Path.of(fileName), response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void getAvatar(int personagemId){
        Personagem personagem = getPersonagem(personagemId);
        getAvatar(personagem);

    }
    public void getAvatar(String name){
        PayloadPersonagemList payloadPersonagemList = buscarPeloNome(name);
        Personagem personagem = payloadPersonagemList.getResults().get(0);
        getAvatar(personagem);

    }

    public PayloadPersonagemList buscarPeloNome(String nome) {
        try {
            String url = "https://rickandmortyapi.com/api/character?name=" + nome;
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI(url)).build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
            PayloadPersonagemList payloadPersonagemList = mapper.readValue(response.body(), PayloadPersonagemList.class);
            return payloadPersonagemList;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}