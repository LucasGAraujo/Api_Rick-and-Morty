package br.infnet;

import br.infnet.exception.PersonagemNotFoundException;
import br.infnet.model.PayloadPersonagemList;
import br.infnet.model.Personagem;
import br.infnet.util.PersonagemUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonagemTests {
    @Test
    @DisplayName("Busca um personagem Pelo Id")
    public void buscaPersonagemPeloId(){
        PersonagemUtil personagemUtil = new PersonagemUtil();
        Personagem rick =  personagemUtil.getPersonagem(1);
        assertEquals("Rick Sanchez", rick.getName());
        assertEquals("Male", rick.getGender());
    }
    @Test
    @DisplayName("Busca um personagem Pela URL")
    public void buscaPersonagemPelaURL(){
        PersonagemUtil personagemUtil = new PersonagemUtil();
        Personagem rick =  personagemUtil.getPersonagem("https://rickandmortyapi.com/api/character/1");
        assertEquals("Rick Sanchez", rick.getName());
        assertEquals("Male", rick.getGender());
    }
    @Test
    @DisplayName("Deve retornar uma exception para um Personagem que nao existe")
    public void buscaPersonagemQueNaoExiste(){
        PersonagemUtil personagemUtil = new PersonagemUtil();
        assertThrows(PersonagemNotFoundException.class, () ->{
            Personagem rick =  personagemUtil.getPersonagem(-1);
        });
    }
    @Test
    @DisplayName("Deve retornar uma lista de Personagens")
    public void getListaDePersonagens(){
        PersonagemUtil personagemUtil = new PersonagemUtil();
        List<String> personagens = List.of(
                "https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2",
                "https://rickandmortyapi.com/api/character/35",
                "https://rickandmortyapi.com/api/character/38",
                "https://rickandmortyapi.com/api/character/62",
                "https://rickandmortyapi.com/api/character/92");
        List<Personagem> personagensList=  personagemUtil.getPersonagens(personagens);
        assertEquals("Rick Sanchez", personagensList.get(0).getName());
        assertEquals(6, personagensList.size());

    }
    @Test
    @DisplayName("Deve retornar uma lista de Personagens Com tamanho fixo")
    public void getListaDePersonagensComSize(){
        PersonagemUtil personagemUtil = new PersonagemUtil();
        List<String> personagens = List.of(
                "https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2",
                "https://rickandmortyapi.com/api/character/35",
                "https://rickandmortyapi.com/api/character/38",
                "https://rickandmortyapi.com/api/character/62",
                "https://rickandmortyapi.com/api/character/92");
        List<Personagem> personagensList=  personagemUtil.getPersonagens(personagens,3);
        assertEquals("Rick Sanchez", personagensList.get(0).getName());
        assertEquals(3, personagensList.size());
    }
    @Test
    @DisplayName("Deve retornar o avatar do Personagem")
    public void getAvatar(){
        PersonagemUtil personagemUtil = new PersonagemUtil();
        Personagem personagem = personagemUtil.getPersonagem(15);
        personagemUtil.getAvatar(personagem);
        String fileName = "avatar/" + personagem.getName() + ".png";
        boolean exists = Files.exists(Path.of(fileName));
        assertTrue(exists);
    }
    @Test
    @DisplayName("Deve buscar um personagem pelo nome")
    public void buscaPersonagemPeloNome(){
        PersonagemUtil personagemUtil = new PersonagemUtil();
        PayloadPersonagemList personagensPayload = personagemUtil.buscarPeloNome("rick");
        assertEquals(107, personagensPayload.getInfo().getCount());

    }
}