package org.example.exceptions;

public class DifferentKindOfPersonException extends Exception{

    public DifferentKindOfPersonException() {
        super("Impossível criar conta conjunta com pessoa física e jurídica");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
