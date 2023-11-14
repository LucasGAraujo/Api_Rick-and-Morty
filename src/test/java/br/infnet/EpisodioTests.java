package br.infnet;


import br.infnet.exception.EpisodioNotFoundException;
import br.infnet.model.Episodio;
import br.infnet.util.EpisodioUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class EpisodioTests {
    @Test
    @DisplayName("Deve retornar um episodio pelo ID")
    public void getEpisodioByIdTest(){
        EpisodioUtil episodioUtil = new EpisodioUtil();
        Episodio episodio = episodioUtil.getById(1);
        assertEquals("Pilot", episodio.getName());
        assertEquals("December 2, 2013", episodio.getAirDate());
    }
    @Test
    @DisplayName("Deve retornar uma exceptpion para um episodio inexistente")
    public void testaEpisodioInexistente(){
        EpisodioUtil episodioUtil = new EpisodioUtil();
        assertThrows(EpisodioNotFoundException.class, () -> {
            Episodio episodio = episodioUtil.getById(-1);
        });
    }
}