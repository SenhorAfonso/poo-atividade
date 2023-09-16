package org.example.exceptions;

public class InvalidTypeOfBankAccount extends Exception{

    public InvalidTypeOfBankAccount() {
        super("Empresa não pode abrir conta conjunta");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
