package org.example.exceptions;

public class NoCivilUnionException extends Exception{

    public NoCivilUnionException() {
        super("Impossível criar uma conta conjunta sem que o títular e o corretista possuam uma união cívil!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
