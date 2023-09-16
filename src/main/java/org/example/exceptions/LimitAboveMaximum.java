package org.example.exceptions;

public class LimitAboveMaximum extends Exception{

    public LimitAboveMaximum() {
        super("O novo limite deve ser no máximo 50% maior que o limite atual.");
    }


    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
