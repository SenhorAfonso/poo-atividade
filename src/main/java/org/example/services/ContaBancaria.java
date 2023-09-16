package org.example.services;

import org.example.exceptions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ContaBancaria implements ContaBancariaInterface{

    private Pessoa titular;
    private Pessoa corretista;
    private Double saldo = 0.0;
    private Double limite = 500.0;
    private Empresa empresa;
    private HashMap<LocalDateTime, Transacao> transacoes = new HashMap<>();

    public ContaBancaria(Pessoa titular, Pessoa corretista) throws Exception {
        this.titular = titular;
        this.corretista = corretista;

    }

    public ContaBancaria(Empresa empresa) throws Exception {
        this.empresa = empresa;

    }

    public String getTitular() {
        return titular.getNome();
    }

    public Pessoa getCorretista() {
        return corretista;
    }

    public Double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public HashMap<LocalDateTime, Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(HashMap<LocalDateTime, Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public Double aplicacao(Double valorInicial, int quantMeses, Double juros) throws NegativeMonthException {

        if(quantMeses < 0) {
            throw new NegativeMonthException();
        }

        return valorInicial * Math.pow(1 + juros, quantMeses);
    }

    public void aumentoAutomaticoLimite(Double valor) {
        double novoLimite = this.getLimite() + (10 * valor) / 100;

        this.setLimite(novoLimite);

    }

    public boolean simularFinanciamento(int quantMeses, Double valor) throws InsufficientLimitException {

        if(valor / quantMeses > this.getLimite()) {
            throw new InsufficientLimitException();
        } else {
            return true;
        }

    }


    public void RealizarDeposito(ContaBancaria destino, Double valor) throws NotEnoughIncomeException {

        if(valor > this.getSaldo()) {
            throw new NotEnoughIncomeException();

        } else {
            this.aumentoAutomaticoLimite(valor);
            this.setSaldo(this.getSaldo() - valor);
            destino.setSaldo(getSaldo() + valor);

            Transacao transacao = new Transacao(LocalDateTime.now(), "Saída", valor);
            this.getTransacoes().put(LocalDateTime.now(), transacao);

            transacao = new Transacao(LocalDateTime.now(), "Entrada", valor);
            destino.getTransacoes().put(LocalDateTime.now(), transacao);

        }

    }

    public void solicitarAumentoLimite(Double valor) throws LimitAboveMaximum {
        if (valor > this.getLimite() + this.getLimite() * 0.5) {
            throw new LimitAboveMaximum();
        } else {
            this.setLimite(valor);
        }
    }

    public List<Transacao> extratoMensal(int mes) throws InvalidMonthException{

        if(mes < 1 || mes > 12) {
            throw new InvalidMonthException();
        }

        List<Transacao> transacao_list = new ArrayList<>();

        for(Map.Entry<LocalDateTime, Transacao> registro : transacoes.entrySet()) {
            if (registro.getKey().getMonthValue() == mes) {
                transacao_list.add(registro.getValue());
            }
        }

        return transacao_list;
    }

    public List<Transacao> extratoMensal(int mesInicio, int mesFinal) throws InvalidMonthException{

        if(mesInicio < 1 || mesInicio > 12 || mesFinal < 1 || mesFinal > 12) {
            throw new InvalidMonthException();
        }

        List<Transacao> transacoes_list = new ArrayList<>();
        for(Map.Entry<LocalDateTime, Transacao> registro : transacoes.entrySet()) {
            if(registro.getKey().getMonthValue() >= mesInicio && registro.getKey().getMonthValue() <= mesFinal) {
                transacoes_list.add(registro.getValue());
            }
        }

        return transacoes_list;
    }

    public List<Transacao> extratoAnual(int ano) throws InvalidYearException{
        if(ano < 0) {
            throw new InvalidYearException();
        }

        List<Transacao> transcao_list = new ArrayList<>();

        for(Map.Entry<LocalDateTime, Transacao> registro : transacoes.entrySet()) {
            if(registro.getKey().getYear() == ano) {
                transcao_list.add(registro.getValue());
            }
        }

        return transcao_list;
    }

    public List<Transacao> extratoAnual(int anoInicio, int anoFinal) throws InvalidYearException{

        if(anoInicio < 0 || anoFinal < 0) {
            throw new InvalidYearException();
        }

        List<Transacao> transacoes_list = new ArrayList<>();

        for(Map.Entry<LocalDateTime, Transacao> registro : transacoes.entrySet()) {
            if(registro.getKey().getYear() >= anoInicio && registro.getKey().getYear() <= anoFinal) {
                transacoes_list.add(registro.getValue());
            }
        }

        return transacoes_list;
    }

}
