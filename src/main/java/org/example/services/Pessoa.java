package org.example.services;

import org.example.exceptions.LimitAboveMaximum;
import org.example.exceptions.NotEnoughIncomeException;

import java.time.LocalDateTime;

public class Pessoa {

    private String CPF;
    private String nome;
    private Integer idade;
    private EstadoCivil estadoCivil;
    private TipoPessoa tipoPessoa;
    private String CNPJ;
    private ContaBancaria conta;
    private Double saldo;
    private Pessoa conjuge;

    public Pessoa(String CPF, String CNPJ, TipoPessoa tipoPessoa, String nome, Integer idade, EstadoCivil estadoCivil, Double saldo) {

        this.tipoPessoa = tipoPessoa;

        if(this.tipoPessoa == TipoPessoa.PESSOA_JURIDICA) {
            this.CPF = null;
            this.CNPJ = CNPJ;
        } else {
            this.CNPJ = null;
            this.CPF = CPF;
        }

        this.nome = nome;
        this.idade = idade;
        this.estadoCivil = estadoCivil;

        if(this.estadoCivil == EstadoCivil.CASADO || this.estadoCivil == EstadoCivil.UNIAO_ESTAVEL) {
            this.conjuge = conjuge;
        } else {
            this.conjuge = null;
        }

        if(saldo == null) {
            this.saldo = 0.0;
        } else {
            this.saldo = saldo;
        }



    }

    public String getNome() {
        return nome;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getConjuge() {
        if (this.conjuge == null) {
            return null;
        }
        return conjuge.getNome();
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void uniao(EstadoCivil tipoUniao, Pessoa conjuge) {
        if(tipoUniao == EstadoCivil.UNIAO_ESTAVEL || tipoUniao == EstadoCivil.CASADO) {
            this.estadoCivil = tipoUniao;
            conjuge.estadoCivil = tipoUniao;

            this.conjuge = conjuge;
            conjuge.conjuge = this;

        }
    }

    public ContaBancaria abrirConta(Pessoa corretista, TipoConta tipoConta) throws Exception {

        if(tipoConta == TipoConta.CONTA_CONJUNTA) {
            return new ContaConjunta(this, corretista);

        } else if (tipoConta == TipoConta.CONTA_POUPANCA) {
            return new ContaPoupanca(this, corretista);

        } else if (tipoConta == TipoConta.CONTA_CORRENTE) {
            return new ContaCorrente(this, corretista);

        } else {
            //CONTA SALARIO
            return new ContaSalario(this, corretista);

        }

    }

    /*
    public void RealizarDeposito(ContaBancaria origem, ContaBancaria destino, Double valor) throws NotEnoughIncomeException {

        if(valor > origem.getSaldo()) {
            throw new NotEnoughIncomeException();

        } else {
            origem.aumentoAutomaticoLimite(valor);
            origem.setSaldo(origem.getSaldo() - valor);
            destino.setSaldo(getSaldo() + valor);

            Transacao transacao = new Transacao(LocalDateTime.now(), "Saída", valor);
            origem.getTransacoes().put(LocalDateTime.now(), transacao);

            transacao = new Transacao(LocalDateTime.now(), "Entrada", valor);
            destino.getTransacoes().put(LocalDateTime.now(), transacao);

        }

    }
    */

    public void RealizarDeposito(ContaBancaria destino, Double valor) throws NotEnoughIncomeException {

        if(valor > this.getSaldo()) {
            throw new NotEnoughIncomeException();

        } else {
            this.setSaldo(this.getSaldo() - valor);
            Transacao transacao = new Transacao(LocalDateTime.now(), "Entrada", valor);
            destino.getTransacoes().put(LocalDateTime.now(), transacao);
            destino.setSaldo(destino.getSaldo() + valor);

        }

    }

    /*
    public void solicitarAumentoLimite(ContaBancaria conta, Double valor) throws LimitAboveMaximum {
        if (valor > conta.getLimite() + conta.getLimite() * 0.5) {
            throw new LimitAboveMaximum();
        } else {
            conta.setLimite(valor);
        }
    }
     */
}
