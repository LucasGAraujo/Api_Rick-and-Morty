package br.infnet.exception;

public class PersonagemNotFoundException extends RuntimeException {
    public PersonagemNotFoundException() {
    }

    public PersonagemNotFoundException(String message) {
        super(message);
    }
}