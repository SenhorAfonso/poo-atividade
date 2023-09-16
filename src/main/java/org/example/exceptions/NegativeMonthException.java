package org.example.exceptions;

public class NegativeMonthException extends Exception{

    public NegativeMonthException() {
        super("O mês não pode ser negativo");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
