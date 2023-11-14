package br.infnet.model;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class Episodio {
    private int id;
    private String name;
    private String airDate;
    private String episode;
    private List<String> characters;
    private String url;
    private LocalDateTime created;

    public String getAir_date() {
        return airDate;
    }
    public void setAir_date(String air_date) {
        this.airDate = air_date;
    }

}




