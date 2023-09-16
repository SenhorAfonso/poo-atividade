package org.example.services;

public class ContaSalario extends ContaBancaria implements ContaBancariaInterface{

    private Pessoa titular;
    private Pessoa corretista;
    private Double saldo = 0.0;
    private TipoConta tipoConta = TipoConta.CONTA_SALARIO;

    public ContaSalario(Pessoa titular, Pessoa corretista) throws Exception {
        super(titular, corretista);
        this.saldo = saldo;
    }

    public ContaSalario(Empresa empresa) throws Exception {
        super(empresa);
    }


}
