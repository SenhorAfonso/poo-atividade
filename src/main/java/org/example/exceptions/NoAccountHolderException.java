package org.example.exceptions;

public class NoAccountHolderException extends Exception{

    public NoAccountHolderException() {
        super("Impossível criar conta conjunta sem segundo corretista!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
