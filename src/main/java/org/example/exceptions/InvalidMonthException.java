package org.example.exceptions;

public class InvalidMonthException extends Exception{

    public InvalidMonthException() {
        super("O mês entrado é inválido");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
