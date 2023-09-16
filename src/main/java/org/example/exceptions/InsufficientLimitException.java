package org.example.exceptions;

public class InsufficientLimitException extends Exception{

    public InsufficientLimitException() {
        super("A conta não possui limite suficiente para o financiamento!");

    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
