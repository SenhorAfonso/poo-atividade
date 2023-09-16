package org.example.services;

import org.example.exceptions.NoMinimumValueException;

public class ContaPoupanca extends ContaBancaria implements ContaBancariaInterface{

    private Pessoa titular;
    private Pessoa corretista;
    private Double saldo = 0.0;
    private TipoConta tipoConta = TipoConta.CONTA_POUPANCA;

    public ContaPoupanca(Pessoa titular, Pessoa corretista) throws Exception {
        super(titular, corretista);

        if(titular.getSaldo() < 50.0) {
            throw new NoMinimumValueException();
        } else {
            this.saldo += 50.0;
            titular.setSaldo(titular.getSaldo() - 50.0);
        }

    }

    public ContaPoupanca(Empresa empresa) throws Exception {
        super(empresa);

        if(titular.getSaldo() < 50.0) {
            throw new NoMinimumValueException();
        } else {
            this.saldo += 50.0;
            titular.setSaldo(titular.getSaldo() - 50.0);
        }

    }

}
