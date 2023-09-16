package org.example.services;

import org.example.exceptions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ContaBancariaInterface {

    public void aumentoAutomaticoLimite(Double valor);

    public boolean simularFinanciamento(int quantMeses, Double valor) throws InsufficientLimitException;

    public void RealizarDeposito(ContaBancaria destino, Double valor) throws NotEnoughIncomeException;

    public void solicitarAumentoLimite(Double valor) throws LimitAboveMaximum;

    public List<Transacao> extratoMensal(int mes) throws InvalidMonthException;

    public List<Transacao> extratoMensal(int mesInicio, int mesFinal) throws InvalidMonthException;

    public List<Transacao> extratoAnual(int ano) throws InvalidYearException;

    public List<Transacao> extratoAnual(int anoInicio, int anoFinal) throws InvalidYearException;

}
