package org.example.services;

public class ContaCorrente extends ContaBancaria implements ContaBancariaInterface{

    private Pessoa titular;
    private Pessoa corretista;
    private Double saldo = 0.0;
    private TipoConta tipoConta = TipoConta.CONTA_CORRENTE;

    public ContaCorrente(Pessoa titular, Pessoa corretista) throws Exception {
        super(titular, corretista);
    }

    public ContaCorrente(Empresa empresa) throws Exception {
        super(empresa);
    }

}
