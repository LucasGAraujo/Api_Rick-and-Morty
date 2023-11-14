package br.infnet.model;

import lombok.Data;

@Data
public class Info {
    private long count;
    private long pages;
    private String next;
    private String prev;
}