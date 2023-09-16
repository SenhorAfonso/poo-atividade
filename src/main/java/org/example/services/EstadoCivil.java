package org.example.services;

public enum EstadoCivil {

    SOLTEIRO(1),
    CASADO(2),
    UNIAO_ESTAVEL(3),
    VIUVO(4),
    DIVORCIADO(5);

    private Integer cod;
    private Pessoa conjuge;

    EstadoCivil(Integer cod) {
        this.cod = cod;
    }



}
