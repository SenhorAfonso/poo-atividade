package org.example.exceptions;

public class NotEnoughIncomeException extends Exception{

    public NotEnoughIncomeException() {
        super("O titular/conta não possui renda suficiente para realizar essa transação");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
