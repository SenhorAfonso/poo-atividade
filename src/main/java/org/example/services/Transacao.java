package org.example.services;

import java.time.LocalDateTime;

public class Transacao {

    private LocalDateTime horario;
    private String tipo;
    private Double valor;

    public Transacao(LocalDateTime horario, String tipo, Double valor) {
        this.horario = horario;
        this.tipo = tipo;
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }
}
