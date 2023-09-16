package org.example.services;

public enum TipoConta {

    CONTA_SALARIO(1),
    CONTA_POUPANCA(2),
    CONTA_CONJUNTA(3),
    CONTA_CORRENTE(4);

    private Integer cod;

    TipoConta(Integer cod) {
        this.cod = cod;
    }

}
