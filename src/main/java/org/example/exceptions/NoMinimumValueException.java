package org.example.exceptions;

public class NoMinimumValueException extends Exception{

    public NoMinimumValueException() {
        super("Contas poupanças devem ser abertas com no mínimo R$50!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
