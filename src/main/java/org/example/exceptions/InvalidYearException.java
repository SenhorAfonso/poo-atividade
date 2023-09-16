package org.example.exceptions;

public class InvalidYearException extends Exception {

    public InvalidYearException() {
        super("O ano entrado é inválido");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
