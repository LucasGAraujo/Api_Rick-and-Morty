package br.infnet.model;

import lombok.Data;

import java.util.List;

@Data
public class PayloadPersonagemList {
    private Info info;
    private List<Personagem> results;


}