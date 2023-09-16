package org.example.services;

import org.example.exceptions.InvalidTypeOfBankAccount;

public class Empresa {

    private String CNPJ;
    private String nome;
    private Pessoa CEO;
    private Double saldo;

    public Empresa(String CNPJ, String nome, Pessoa CEO, Double saldo) {
        this.CNPJ = CNPJ;
        this.nome = nome;
        this.CEO = CEO;
        this.saldo = saldo;
    }

    public ContaBancaria abrirConta(Empresa empresa, TipoConta tipoConta) throws Exception {

        if(tipoConta == TipoConta.CONTA_CONJUNTA) {
            throw new InvalidTypeOfBankAccount();

        } else if (tipoConta == TipoConta.CONTA_POUPANCA) {
            return new ContaPoupanca(this);

        } else if (tipoConta == TipoConta.CONTA_CORRENTE) {
            return new ContaCorrente(this);

        } else {
            return new ContaSalario(this);

        }

    }


}
